/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.annotation;

import java.lang.reflect.Field;

import org.fujion.component.BaseComponent;

/**
 * Scans an object's class and superclasses for fields marked for wiring. Only fields that extend
 * BaseComponent are eligible for wiring.
 */
public class WiredComponentScanner {
    
    private WiredComponentScanner() {
    }
    
    /**
     * Wire an object instance using the root component to resolve component names.
     *
     * @param instance The object whose fields are to be scanned.
     * @param root The root component used to resolve component names.
     */
    public static void wire(Object instance, BaseComponent root) {
        Class<?> clazz = instance.getClass();
        
        while (clazz != Object.class) {
            wire(instance, root, clazz);
            clazz = clazz.getSuperclass();
        }
    }
    
    private static void wire(Object instance, BaseComponent root, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            WiredComponent annot = field.getAnnotation(WiredComponent.class);
            
            if (annot == null) {
                continue;
            }
            
            OnFailure onFailure = annot.onFailure();
            
            if (!BaseComponent.class.isAssignableFrom(field.getType())) {
                onFailure.doAction("Field \"%s\" is not a component type", field.getName());
                return;
            }
            
            try {
                if (!annot.overwrite() && field.get(instance) != null) {
                    onFailure.doAction("Field \"%s\" is already assigned a value", field.getName());
                    continue;
                }
                
                String name = annot.value();
                name = name.isEmpty() ? field.getName() : name;
                BaseComponent component = root.findByName(name);
                
                if (component == null) {
                    onFailure.doAction("No component matching name \"%s\"", name);
                    continue;
                }
                
                field.set(instance, component);
            } catch (Exception e) {
                onFailure.doAction(e);
            }
        }
    }
    
}

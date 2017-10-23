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
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.reflect.FieldUtils;
import org.fujion.ancillary.ComponentException;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;
import org.fujion.event.IEventListener;
import org.springframework.util.ReflectionUtils;

/**
 * Wires {@literal @EventHandler}-annotated methods.
 */
public class EventHandlerScanner {

    /**
     * Event listener implementation that invokes an {@literal @EventHandler}-annotated method on a
     * target object.
     */
    private static class EventListener implements IEventListener {

        private final Object target;

        private final Method method;

        EventListener(Object target, Method method) {
            this.target = target;
            this.method = method;
        }

        /**
         * Pass an event to the handler method on the target.
         */
        @Override
        public void onEvent(Event event) {
            try {
                if (method.getParameterTypes().length == 0) {
                    method.invoke(target);
                } else {
                    method.invoke(target, event);
                }
            } catch (Throwable e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            
            if (!(object instanceof EventListener)) {
                return false;
            }
            
            EventListener el = (EventListener) object;
            return target == el.target && method.equals(el.method);
        }

        @Override
        public int hashCode() {
            return target.hashCode() ^ method.hashCode();
        }
    }

    /**
     * Recursively scans the controller's class and superclasses for {@literal @EventHandler}
     * -annotated methods.
     *
     * @param instance Controller to be wired.
     * @param root The root component used to resolve component names.
     */
    public static void wire(Object instance, BaseComponent root) {
        Class<?> clazz = instance.getClass();

        while (clazz != Object.class) {
            wire(instance, root, clazz);
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Wires onEvent style event handlers.
     *
     * @param instance Controller to be wired.
     * @param component The component that triggers the event.
     * @param onHandlers The map of onEvent style handlers.
     */
    public static void wire(Object instance, BaseComponent component, Map<String, String> onHandlers) {
        for (Entry<String, String> entry : onHandlers.entrySet()) {
            String eventType = entry.getKey();
            String methodName = entry.getValue();
            Class<? extends Event> eventClass = EventUtil.getEventClass(eventType);
            Method handler = ReflectionUtils.findMethod(instance.getClass(), methodName, eventClass);
            handler = handler == null ? ReflectionUtils.findMethod(instance.getClass(), methodName) : handler;

            if (handler == null) {
                throw new ComponentException("A suitable event handler named \"" + methodName + "\"could not be found");

            }

            EventListener eventListener = new EventListener(instance, handler);
            component.addEventListener(eventType, eventListener);
        }
    }

    /**
     * Scans the specified class for {@literal @EventHandler}-annotated methods.
     *
     * @param instance Controller to be wired.
     * @param root The root component used to resolve component names.
     * @param clazz The class to be scanned.
     */
    private static void wire(Object instance, BaseComponent root, Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);
            EventHandler[] annotations = method.getAnnotationsByType(EventHandler.class);

            for (EventHandler annot : annotations) {
                OnFailure onFailure = annot.onFailure();
                
                if (!validateSignature(method)) {
                    onFailure.doAction(
                        "Method " + method.getName() + " signature does not conform to that of an event handler.");
                    break;
                }

                Set<String> targets = asSet(annot.target());
                Set<String> types = asSet(annot.value());
                BaseComponent component = null;

                if (types.isEmpty()) {
                    onFailure.doAction("At least one event type must be specified");
                }

                if (targets.isEmpty()) {
                    targets.add("self");
                }

                for (String target : targets) {
                    if ("self".equals(target)) {
                        component = isComponent(clazz) ? (BaseComponent) instance : root;
                    } else if (target.startsWith("@")) {
                        Field field = findField(clazz, target.substring(1));

                        if (field != null) {
                            try {
                                field.setAccessible(true);
                                component = (BaseComponent) field.get(instance);
                            } catch (Exception e) {
                                // what to do here
                            }
                        }
                    } else {
                        component = root == null ? null : root.findByName(target);
                    }

                    if (component == null) {
                        onFailure.doAction("No suitable event handler target found for \"" + target + "\"");
                    } else {
                        for (String type : types) {
                            component.addEventListener(type, new EventListener(instance, method), annot.syncToClient());
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Validates if the method signature is suitable for an event handler.
     *
     * @param method The method to validate.
     * @return True if the method is suitable as an event handler.
     */
    private static boolean validateSignature(Method method) {
        Class<?>[] params = method.getParameterTypes();
        return params.length == 0 || (params.length == 1 && Event.class.isAssignableFrom(params[0]));
    }

    /**
     * Converts an array of string values to a set. This effectively removes duplicate and empty
     * entries.
     *
     * @param values Array of string values.
     * @return Corresponding set of values.
     */
    private static Set<String> asSet(String[] values) {
        Set<String> set = new HashSet<>();

        for (String value : values) {
            if (!value.isEmpty()) {
                set.add(value);
            }
        }

        return set;
    }

    /**
     * Returns true if the class is a component class.
     *
     * @param clazz Class to be examined.
     * @return True if the class is a component class.
     */
    private static boolean isComponent(Class<?> clazz) {
        return BaseComponent.class.isAssignableFrom(clazz);
    }

    /**
     * Returns the field definition for the named field. Field must be a component type.
     *
     * @param clazz Class to be searched.
     * @param name Name of the field.
     * @return The corresponding field definition, or null if no matching field was found.
     */
    private static Field findField(Class<?> clazz, String name) {
        try {
            Field result = FieldUtils.getField(clazz, name, true);
            return isComponent(result.getType()) ? result : null;
        } catch (Exception e) {
            return null;
        }
    }

    private EventHandlerScanner() {
    }

}

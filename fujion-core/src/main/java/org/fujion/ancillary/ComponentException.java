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
package org.fujion.ancillary;

import java.lang.reflect.InvocationTargetException;

import org.fujion.component.BaseComponent;
import org.springframework.core.NestedRuntimeException;

/**
 * Run time exception related to a component operation.
 */
public class ComponentException extends NestedRuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private final BaseComponent component;
    
    private final Class<? extends BaseComponent> componentClass;
    
    private static String formatMessage(Class<?> componentClass, BaseComponent component, String message, Object... args) {
        Object object = component != null ? component : componentClass;
        return (object == null ? "" : object + ": ") + String.format(message, args);
    }

    private static Throwable getCause(Throwable cause) {
        return cause instanceof InvocationTargetException ? cause.getCause() : cause;
    }
    
    private ComponentException(Throwable cause, Class<? extends BaseComponent> componentClass, BaseComponent component,
        String message, Object... args) {
        super(formatMessage(componentClass, component, message, args), getCause(cause));
        this.component = component;
        this.componentClass = component != null ? component.getClass() : componentClass;
    }

    public ComponentException(Throwable cause, String message, Object... args) {
        this(cause, null, null, message, args);
    }

    public ComponentException(Throwable cause, Class<? extends BaseComponent> componentClass, String message,
        Object... args) {
        this(cause, componentClass, null, message, args);
    }

    public ComponentException(Throwable cause, BaseComponent component, String message, Object... args) {
        this(cause, null, component, message, args);
    }

    public ComponentException(String message, Object... args) {
        this(null, null, null, message, args);
    }
    
    public ComponentException(Class<? extends BaseComponent> componentClass, String message, Object... args) {
        this(null, componentClass, null, message, args);
    }
    
    public ComponentException(BaseComponent component, String message, Object... args) {
        this(null, null, component, message, args);
    }
    
    /**
     * Returns the component instance that caused the exception.
     *
     * @return Component instance that caused the exception. May be null.
     */
    public BaseComponent getComponent() {
        return component;
    }
    
    /**
     * Returns the class of the component that caused the exception. If a component instance is
     * associated with the exception, the class will be that of the component instance. However, if
     * the exception occurred while attempting to create an instance of a component, the component
     * instance will be null and only the component class will be set.
     *
     * @return Class of the component that caused the exception. May be null.
     */
    public Class<? extends BaseComponent> getComponentClass() {
        return componentClass;
    }
    
}

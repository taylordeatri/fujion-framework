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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.IntSupplier;

import org.apache.commons.beanutils.ConstructorUtils;
import org.fujion.ancillary.ComponentException;
import org.fujion.ancillary.ComponentFactory;
import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.DeferredInvocation;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.FactoryParameter;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.model.IBinding;

/**
 * Stores metadata about a component, as derived from component annotations.
 */
public class ComponentDefinition {
    
    /**
     * Represents the cardinality of a child tag.
     */
    public static class Cardinality {
        
        private final int minimum;
        
        private final int maximum;
        
        Cardinality(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }
        
        /**
         * Returns the minimum cardinality.
         *
         * @return The minimum cardinality.
         */
        public int getMinimum() {
            return minimum;
        }
        
        /**
         * Returns the maximum cardinality.
         *
         * @return The maximum cardinality.
         */
        public int getMaximum() {
            return maximum;
        }
        
        /**
         * Returns true if there is a minimum cardinality.
         *
         * @return True if there is a minimum cardinality.
         */
        public boolean hasMinimum() {
            return minimum > 0;
        }
        
        /**
         * Returns true if there is a maximum cardinality.
         *
         * @return True if there is a maximum cardinality.
         */
        public boolean hasMaximum() {
            return maximum != Integer.MAX_VALUE;
        }
        
        /**
         * Returns true if the count falls within the cardinality constraints.
         *
         * @param count The count to test.
         * @return True if the count falls within the cardinality constraints.
         */
        public boolean isValid(int count) {
            return count >= minimum && count <= maximum;
        }
    }
    
    private final ContentHandling contentHandling;
    
    private final String tag;
    
    private final Class<? extends BaseComponent> componentClass;
    
    private final Class<? extends ComponentFactory> factoryClass;

    private final String widgetModule;
    
    private final String widgetClass;
    
    private final Set<String> parentTags = new HashSet<>();
    
    private final Map<String, Cardinality> childTags = new HashMap<>();
    
    private final Map<String, Method> getters = new HashMap<>();
    
    private final Map<String, Method> setters = new HashMap<>();
    
    private final Map<String, Method> parameters = new HashMap<>();
    
    /**
     * Creates a component definition derived from annotation information within the specified
     * class.
     *
     * @param componentClass A component class.
     */
    public ComponentDefinition(Class<? extends BaseComponent> componentClass) {
        Component annot = componentClass.getAnnotation(Component.class);
        this.componentClass = componentClass;
        this.factoryClass = annot.factoryClass();
        this.widgetModule = annot.widgetModule();
        this.widgetClass = annot.widgetClass();
        this.tag = annot.tag();
        this.contentHandling = annot.content();
        
        for (String tag : annot.parentTag()) {
            addParentTag(tag);
        }
        
        for (ChildTag tag : annot.childTag()) {
            addChildTag(tag);
        }
        
    }
    
    /**
     * Returns The value of the named property.
     *
     * @param instance Instance to retrieve property from.
     * @param name Name of property.
     * @return The property value.
     */
    public Object getProperty(BaseComponent instance, String name) {
        String[] pname = parsePropertyName(name);
        String key = pname[0];
        name = pname[1];
        Method setter = setters.get(key);
        Method getter = getters.get(key);
        
        if (getter == null) {
            String message = setter != null ? "Property is write-only" : "Property is not recognized";
            throw new ComponentException(message + ": " + name);
        }
        
        try {
            return getter.invoke(instance, getter.getParameterCount() == 1 ? new Object[] { name } : null);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    /**
     * Sets a property value or defers that operation if the property is marked as such.
     *
     * @param instance Instance containing the property or attribute map.
     * @param name Name of property or attribute. If prefixed with "@", is interpreted as an
     *            attribute name; otherwise as a property name.
     * @param value The value to set.
     * @return Null if the operation occurred, or a DeferredExecution object if deferred.
     */
    public DeferredInvocation<?> setProperty(BaseComponent instance, String name, Object value) {
        String[] pname = parsePropertyName(name);
        String key = pname[0];
        name = pname[1];
        Method setter = setters.get(key);
        Method getter = getters.get(key);
        
        if (value instanceof IBinding) {
            ((IBinding) value).init(instance, name, getter, setter);
            return null;
        }

        if (setter == null) {
            if (parameters.containsKey(name)) {
                return null;
            }
            
            String message = getter != null ? "Property is read-only" : "Property is not recognized";
            throw new ComponentException(message + ": " + name);
        }
        
        Object[] args = setter.getParameterCount() == 1 ? new Object[] { value } : new Object[] { name, value };
        
        if (setter.getAnnotation(PropertySetter.class).defer()) {
            return new DeferredInvocation<>(instance, setter, args);
        }

        ConvertUtil.invokeMethod(instance, setter, args);
        return null;
    }
    
    /**
     * Parses property name into 2 element array: [0] = property key, [1] = property name
     *
     * @param name Property name to parse.
     * @return Parsed name.
     */
    private String[] parsePropertyName(String name) {
        if (!name.contains(":")) {
            return new String[] { name, name };
        }

        String[] pname = name.split("\\:", 2);
        pname[0] += ":";
        return pname;
    }
    
    /**
     * Returns the XML tag for this component type.
     *
     * @return An XML tag.
     */
    public String getTag() {
        return tag;
    }
    
    /**
     * Returns the implementation class for this component type.
     *
     * @return Implementation class.
     */
    public Class<? extends BaseComponent> getComponentClass() {
        return componentClass;
    }
    
    /**
     * Returns the factory class for this component type.
     *
     * @return Factory class.
     */
    public Class<? extends ComponentFactory> getFactoryClass() {
        return factoryClass;
    }
    
    /**
     * Returns a factory instance for this component.
     *
     * @return The component factory.
     */
    public ComponentFactory getFactory() {
        try {
            return ConstructorUtils.invokeConstructor(factoryClass, this);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    /**
     * Returns the javascript module containing the widget class.
     *
     * @return Widget module.
     */
    public String getWidgetModule() {
        return widgetModule;
    }
    
    /**
     * Returns the javascript class for the widget.
     *
     * @return Widget class.
     */
    public String getWidgetClass() {
        return widgetClass;
    }
    
    /**
     * Returns the cardinality of a child tag.
     *
     * @param childTag A child tag.
     * @return Cardinality of the child tag, or null if the tag is not a valid child.
     */
    public Cardinality getCardinality(String childTag) {
        Cardinality cardinality = childTags.get(childTag);
        return cardinality == null ? childTags.get("*") : cardinality;
    }
    
    /**
     * Returns an immutable map of all child tags.
     *
     * @return Map of child tags.
     */
    public Map<String, Cardinality> getChildTags() {
        return Collections.unmodifiableMap(childTags);
    }
    
    /**
     * Returns true if this component allows children.
     *
     * @return True if this component allows children.
     */
    public boolean childrenAllowed() {
        return childTags.size() > 0;
    }
    
    /**
     * Validate that a child defined by the component definition is valid for this parent.
     *
     * @param childDefinition Definition for child component.
     * @param childCount Current child count.
     * @exception ComponentException Thrown if child fails validation.
     */
    public void validateChild(ComponentDefinition childDefinition, IntSupplier childCount) {
        if (!childrenAllowed()) {
            throw new ComponentException(componentClass, "Children are not allowed");
        }
        
        childDefinition.validateParent(this);
        Cardinality cardinality = getCardinality(childDefinition.tag);
        
        if (cardinality == null) {
            throw new ComponentException(componentClass, "%s is not a valid child", childDefinition.componentClass);
        }
        
        if (cardinality.hasMaximum() && childCount.getAsInt() >= cardinality.getMaximum()) {
            throw new ComponentException(componentClass, "A maximum of %d children of type %s are allowed",
                    cardinality.getMaximum(), childDefinition.componentClass);
        }
        
    }
    
    /**
     * Validate that a component defined by the component definition is a valid parent for this
     * component.
     *
     * @param parentDefinition Definition for parent component.
     * @exception ComponentException Thrown if child fails validation.
     */
    public void validateParent(ComponentDefinition parentDefinition) {
        if (!isParentTag(parentDefinition.tag)) {
            throw new ComponentException(componentClass, "%s is not a valid parent", parentDefinition.componentClass);
        }
    }
    
    /**
     * Returns true if the tag is a valid parent tag.
     *
     * @param tag Tag to be tested.
     * @return True if the tag is a valid parent tag.
     */
    public boolean isParentTag(String tag) {
        return parentTags.contains(tag) || parentTags.contains("*");
    }
    
    /**
     * Returns an immutable set of parent tags.
     *
     * @return Set of valid parent tags.
     */
    public Set<String> getParentTags() {
        return Collections.unmodifiableSet(parentTags);
    }
    
    /**
     * Returns how to handle content for this component type.
     *
     * @return How to handle content.
     */
    public ContentHandling contentHandling() {
        return contentHandling;
    }
    
    // Processors for component annotations
    
    /**
     * Registers a parent tag.
     *
     * @param tag The tag, or "*" to indicate any parent tag is valid.
     */
    private void addParentTag(String tag) {
        parentTags.add(tag);
    }
    
    /**
     * Registers a child tag.
     *
     * @param tag A child tag.
     */
    private void addChildTag(ChildTag tag) {
        childTags.put(tag.value(), new Cardinality(tag.minimum(), tag.maximum()));
    }
    
    /**
     * Returns true if the method is static.
     *
     * @param method Method to test.
     * @return True if the method is static.
     */
    private boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }
    
    /**
     * Registers a property getter.
     *
     * @param method The getter method.
     */
    /*package*/ void _addGetter(Method method) {
        PropertyGetter getter = method.getAnnotation(PropertyGetter.class);
        
        if (getter != null) {
            String name = getter.value();

            if (!this.getters.containsKey(name)) {
                if (isStatic(method) || method.getReturnType() == Void.TYPE || method.getParameterTypes().length > 0) {
                    throw new IllegalArgumentException("Bad signature for getter method: " + method.getName());
                }

                this.getters.put(name, getter.hide() ? null : method);
            }
        }
    }
    
    /**
     * Returns an immutable map of getter methods.
     *
     * @return Map of getter methods.
     */
    public Map<String, Method> getGetters() {
        return Collections.unmodifiableMap(getters);
    }
    
    /**
     * Registers a property setter.
     *
     * @param method The setter method.
     */
    /*package*/ void _addSetter(Method method) {
        PropertySetter setter = method.getAnnotation(PropertySetter.class);
        
        if (setter != null) {
            String name = setter.value();
            
            if (!setters.containsKey(name)) {
                int length = method.getParameterCount();
                
                if (isStatic(method) || length == 0 || length > 2
                        || (length == 2 && method.getParameterTypes()[0] != String.class)) {
                    throw new IllegalArgumentException("Bad signature for setter method: " + method.getName());
                }
                
                setters.put(name, setter.hide() ? null : method);
            }
        }
    }
    
    /**
     * Returns an immutable map of setter methods.
     *
     * @return Map of setter methods.
     */
    public Map<String, Method> getSetters() {
        return Collections.unmodifiableMap(setters);
    }
    
    /**
     * Registers a factory parameter.
     *
     * @param method The static processor method.
     */
    /*package*/ void _addFactoryParameter(Method method) {
        FactoryParameter parameter = method.getAnnotation(FactoryParameter.class);

        if (parameter != null) {
            String name = parameter.value();
            
            if (!parameters.containsKey(name)) {
                if (isStatic(method) || method.getParameterTypes().length != 1) {
                    throw new IllegalArgumentException("Bad signature for factory parameter method: " + method.getName());
                }
                
                parameters.put(name, method);
            }
        }
    }
    
    /**
     * Returns an immutable map of factory parameters.
     *
     * @return Map of factory parameters.
     */
    public Map<String, Method> getFactoryParameters() {
        return Collections.unmodifiableMap(parameters);
    }
    
    @Override
    public boolean equals(Object object) {
        return object instanceof ComponentDefinition && ((ComponentDefinition) object).componentClass == componentClass;
    }
}

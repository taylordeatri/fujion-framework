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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.fujion.ancillary.ComponentFactory;

/**
 * Class annotation to control deserialization of a Fujion resource.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

    /**
     * Methods of handling text content nodes.
     */
    public enum ContentHandling {
        /**
         * Text content throws an exception.
         */
        ERROR,
        /**
         * Text content is ignored.
         */
        IGNORE,
        /**
         * Text content is internally represented as an attribute named #text.
         */
        AS_ATTRIBUTE,
        /**
         * Text content is internally represented as a child component of the class Content.
         */
        AS_CHILD
    }

    /**
     * Marks a property getter.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface PropertyGetter {

        /**
         * The property name.
         *
         * @return The property name.
         */
        String value();

        /**
         * If true, hide the getter method from the deserializer. Use this to hide a getter
         * annotated in a superclass.
         *
         * @return If true, hide the getter method from the deserializer.
         */
        boolean hide() default false;
    }

    /**
     * Marks a property setter
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface PropertySetter {

        /**
         * The property name.
         *
         * @return The property name.
         */
        String value();

        /**
         * If true, hide the setter method from the deserializer. Use this to hide a setter
         * annotated in a superclass.
         *
         * @return If true, hide the setter method from the deserializer.
         */
        boolean hide() default false;

        /**
         * If true, defer invoking the setter until deserialization is complete.
         *
         * @return If true, defer invoking the setter until deserialization is complete.
         */
        boolean defer() default false;

    }

    /**
     * Binds a factory parameter to an XML attribute. Such attributes are used to modify factory
     * settings that affect component creation.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface FactoryParameter {

        /**
         * The attribute name.
         *
         * @return The attribute name.
         */
        String value();

    }

    /**
     * Represents a child tag and its cardinality.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    public @interface ChildTag {

        /**
         * The child tag.
         *
         * @return The child tag.
         */
        String value();

        /**
         * Minimum number of occurrences.
         *
         * @return Minimum number of occurrences.
         */
        int minimum() default 0;

        /**
         * Maximum number of occurrences.
         *
         * @return Maximum number of occurrences.
         */
        int maximum() default Integer.MAX_VALUE;

    }

    /**
     * The XML tag corresponding to this component.
     *
     * @return The XML tag corresponding to this component.
     */
    String tag();

    /**
     * How to handle text content associated with the tag.
     *
     * @return The content handling.
     */
    ContentHandling content() default ContentHandling.ERROR;

    /**
     * The allowable parent tag(s) for this component.
     *
     * @return The allowable parent tag(s).
     */
    String[] parentTag() default {};

    /**
     * The allowable child tag(s) for this component, including cardinality.
     *
     * @return The allowable child tag(s).
     */
    ChildTag[] childTag() default {};

    /**
     * The class of the factory for creating this component.
     *
     * @return The factory class.
     */
    Class<? extends ComponentFactory> factoryClass() default ComponentFactory.class;

    /**
     * The JavaScript module containing the widget.
     *
     * @return The JavaScript module name.
     */
    String widgetModule() default "fujion-widget";

    /**
     * The JavaScript class for the widget.
     *
     * @return The JavaScript widget class.
     */
    String widgetClass();
    
}

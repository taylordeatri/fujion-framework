/*
 * #%L
 * carewebframework
 * %%
 * Copyright (C) 2008 - 2016 Regenstrief Institute, Inc.
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
 * This Source Code Form is also subject to the terms of the Health-Related
 * Additional Disclaimer of Warranty and Limitation of Liability available at
 *
 *      http://www.carewebframework.org/licensing/disclaimer.
 *
 * #L%
 */
package org.fujion.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Abstract base class for implementing custom scopes.
 */
public abstract class AbstractScope implements Scope {

    private static final Log log = LogFactory.getLog(AbstractScope.class);

    /**
     * IOC container for the custom scopes.
     */
    public static class ScopeContainer implements Scope {

        private final Map<String, Object> beans = new HashMap<>();

        private final Map<String, Runnable> destructionCallbacks = new HashMap<>();

        private final String conversationId;

        public ScopeContainer(String conversationId) {
            this.conversationId = conversationId;
        }

        @Override
        public synchronized Object remove(String key) {
            destructionCallbacks.remove(key);
            return beans.remove(key);
        }

        @Override
        public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
            Object bean = beans.get(name);

            if (bean == null) {
                bean = objectFactory.getObject();
                beans.put(name, bean);
            }

            return bean;
        }

        /**
         * Register a bean destruction callback.
         *
         * @param name Bean name.
         * @param callback Callback.
         */
        @Override
        public synchronized void registerDestructionCallback(String name, Runnable callback) {
            destructionCallbacks.put(name, callback);
        }

        /**
         * For orphan containers.
         */
        @Override
        protected void finalize() throws Throwable {
            destroy();
            super.finalize();
        }

        /**
         * Calls all registered destruction callbacks and removes all bean references from the
         * container.
         */
        public void destroy() {
            for (Entry<String, Runnable> entry : destructionCallbacks.entrySet()) {
                try {
                    entry.getValue().run();
                } catch (Throwable t) {
                    log.error("Error during destruction callback for bean " + entry.getKey(), t);
                }
            }

            beans.clear();
            destructionCallbacks.clear();
        }

        @Override
        public Object resolveContextualObject(String key) {
            return null;
        }

        @Override
        public String getConversationId() {
            return conversationId;
        }
    }

    /**
     * Implement to retrieve the container for this scope.
     *
     * @return Container for this scope.
     */
    public abstract ScopeContainer getContainer();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        return getContainer().get(name, objectFactory);
    }

    @Override
    public Object remove(String name) {
        return getContainer().remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        getContainer().registerDestructionCallback(name, callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return getContainer().resolveContextualObject(key);
    }

    @Override
    public String getConversationId() {
        ScopeContainer container = getContainer();
        return container == null ? null : container.getConversationId();
    }

}

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
package org.fujion.theme;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Theme implementation that can pull from multiple sources.
 */
public class Theme {
    
    private final String name;
    
    private final ObjectNode config;
    
    private volatile String init;
    
    /**
     * Create a theme.
     *
     * @param name The unique theme name.
     * @param config The SystemJS configuration that will override the default theme.
     */
    public Theme(String name, ObjectNode config) {
        this.name = name;
        this.config = config;
    }

    /**
     * Returns the unique theme name.
     *
     * @return The unique theme name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the SystemJS configuration that will override the default theme.
     *
     * @return The SystemJS configuration that will override the default theme.
     */
    protected ObjectNode getConfig() {
        return config;
    }

    /**
     * Returns the web jar initialization string as overridden by this theme.
     *
     * @return The web jar initialization string as overridden by this theme.
     */
    public String getWebJarInit() {
        return init == null ? _getWebJarInit() : init;
    }
    
    private synchronized String _getWebJarInit() {
        return init == null ? init = config.toString() : init;
    }
    
}

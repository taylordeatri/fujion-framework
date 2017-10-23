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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.client.WebJarLocator;
import org.fujion.common.AbstractRegistry;
import org.fujion.common.MiscUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * A registry of all themes discovered during application startup. Themes are loaded from
 * <i>theme.properties</i> files located in either the WEB-INF or META-INF folder. A
 * <i>theme.properties</i> file can contain one or more SystemJS overrides that change the default
 * theme. The format is:
 *
 * <pre>
 * &lt;theme-name&gt;/&lt;path to config entry&gt;=&lt;override value&gt;
 * </pre>
 *
 * For example, to override the default Bootstrap theme with one of the publicly available
 * alternates:
 *
 * <pre>
 *      cosmo/paths/bootstrap-css=webjars/bootswatch-cosmo/css/bootstrap.css
 *      cerulean/paths/bootstrap-css=webjars/bootswatch-cerulean/css/bootstrap.css
 *      cyborg/paths/bootstrap-css=webjars/bootswatch-cyborg/css/bootstrap.css
 *      darkly/paths/bootstrap-css=webjars/bootswatch-darkly/css/bootstrap.css
 *      sandstone/paths/bootstrap-css=webjars/bootswatch-sandstone/css/bootstrap.css
 *      slate/paths/bootstrap-css=webjars/bootswatch-slate/css/bootstrap.css
 * </pre>
 */
public class ThemeRegistry extends AbstractRegistry<String, Theme> implements ApplicationContextAware {
    
    private static final Log log = LogFactory.getLog(ThemeRegistry.class);
    
    private static final ThemeRegistry instance = new ThemeRegistry();

    /**
     * Returns a singleton instance of the theme registry.
     *
     * @return Singleton instance of the theme registry.
     */
    public static ThemeRegistry getInstance() {
        return instance;
    }

    @Override
    protected String getKey(Theme theme) {
        return theme.getName();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        loadThemes(applicationContext, "classpath*:META-INF");
        loadThemes(applicationContext, "WEB-INF");
    }

    /**
     * Locates and processes <i>theme.properties</i> files. This will create and register new themes
     * based on the contents of these files.
     *
     * @param applicationContext The application context to use to locate files.
     * @param path The root path to search for files.
     */
    private void loadThemes(ApplicationContext applicationContext, String path) {
        try {
            for (Resource resource : applicationContext.getResources(path + "/theme.properties")) {
                try (InputStream in = resource.getInputStream()) {
                    Properties props = new Properties();
                    props.load(in);
                    
                    for (Entry<Object, Object> entry : props.entrySet()) {
                        String key = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        updateTheme(key, value);
                    }
                } catch (FileNotFoundException e) {
                    // ignore
                } catch (Exception e) {
                    log.error("Error reading theme configuration data from " + resource, e);
                }
            }
        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Fetches the named theme from the registry. If one does not exist, it is created and
     * registered.
     *
     * @param themeName The theme name.
     * @return The corresponding theme.
     */
    private Theme getOrCreateTheme(String themeName) {
        Theme theme = get(themeName);

        if (theme == null) {
            theme = new Theme(themeName, WebJarLocator.getInstance().getConfig());
            register(theme);
            log.info("Registered theme: " + themeName);
        }

        return theme;
    }

    /**
     * Updates a theme's configuration.
     *
     * @param configPath The "/"-delimited path to the configuration node to be updated. The first
     *            piece of the path is the name of the theme to be modified.
     * @param value The new value. If null or empty, the configuration node will be removed.
     */
    private void updateTheme(String configPath, String value) {
        String[] path = configPath.split("\\/");
        Theme theme = getOrCreateTheme(path[0]);
        ObjectNode config = theme.getConfig();
        ObjectNode node = config;
        int last = path.length - 1;
        value = StringUtils.trimToNull(value);
        
        for (int i = 1; i <= last; i++) {
            String pnode = path[i];
            ObjectNode parent = node;
            
            if (i == last) {
                if (value == null) {
                    parent.remove(pnode);
                } else {
                    parent.set(pnode, new TextNode(value));
                }
                
                break;
            }
            
            node = (ObjectNode) parent.get(pnode);
            
            if (node == null) {
                if (value == null) {
                    return;
                } else {
                    node = config.objectNode();
                    parent.set(pnode, node);
                }
            }
        }
    }

}

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
package org.fujion.highcharts;

import java.lang.reflect.Field;

import org.fujion.common.StrUtil;
import org.fujion.ancillary.Options;

/**
 * Global settings for Highcharts. These are set on a per desktop basis. Values are loaded from
 * label properties. For example, to set the decimal separator in the lang options:
 * 
 * <pre>
 * highcharts.lang.decimalPoint=.
 * </pre>
 */
public class GlobalSettings extends Options {
    
    private static final String LABEL_PREFIX = "highcharts.";
    
    public final GlobalOptions global = new GlobalOptions();
    
    public final LanguageOptions lang = new LanguageOptions();
    
    /**
     * Create and load global settings.
     */
    protected GlobalSettings() {
        super();
        loadSettings("global.", global);
        loadSettings("lang.", lang);
    }
    
    /**
     * Load global settings into the specified options object.
     * 
     * @param cat This is the category prefix for the options.
     * @param options The options instance to receive the settings.
     */
    private void loadSettings(String cat, Options options) {
        for (Field field : options.getClass().getFields()) {
            try {
                String value = StrUtil.getLabel(LABEL_PREFIX + cat + field.getName());
                
                if (value != null && !value.isEmpty()) {
                    Class<?> type = field.getType();
                    
                    if (type.isArray()) {
                        field.set(options, value.split("\\,"));
                    } else if (type == Boolean.class) {
                        field.set(options, Boolean.valueOf(value));
                    } else {
                        field.set(options, value);
                    }
                }
            } catch (Exception e) {}
        }
    }
}

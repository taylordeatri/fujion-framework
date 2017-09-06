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
package org.fujion.component;

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * Base for components that implement scripting support.
 */
public class BaseScriptComponent extends BaseSourcedComponent {

    protected BaseScriptComponent(boolean contentSynced) {
        super(contentSynced);
    }
    
    protected BaseScriptComponent(String content, boolean contentSynced) {
        super(content, contentSynced);
    }
    
    private boolean defer;

    private String type;
    
    /**
     * Returns the defer flag. If true, script execution is deferred (the exact timing is
     * implementation-dependent). Otherwise it is immediate.
     *
     * @return The defer flag.
     */
    @PropertyGetter("defer")
    public boolean getDefer() {
        return defer;
    }

    /**
     * Sets the defer flag. If true, script execution is deferred (the exact timing is
     * implementation-dependent). Otherwise it is immediate.
     *
     * @param defer The defer flag.
     */
    @PropertySetter("defer")
    public void setDefer(boolean defer) {
        if (defer != this.defer) {
            this.defer = defer;
            
            if (isContentSynced()) {
                sync("defer", defer);
            }
        }
    }

    /**
     * Returns the type of script.
     *
     * @return The script type.
     */
    @PropertyGetter("type")
    public String getType() {
        return type;
    }

    /**
     * Sets the type of script.
     *
     * @param type The script type.
     */
    @PropertySetter("type")
    public void setType(String type) {
        if (!areEqual(type = nullify(type), this.type)) {
            this.type = type;

            if (isContentSynced()) {
                sync("type", type);
            }
        }
    }

}

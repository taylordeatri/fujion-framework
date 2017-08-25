/*
 * #%L
 * fujion
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
 * #L%
 */
package org.fujion.component;

import org.fujion.ancillary.MimeContent;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.LoadEvent;

/**
 * An iframe component.
 */
@Component(tag = "iframe", widgetClass = "Iframe", parentTag = "*")
public class Iframe extends BaseUIComponent {
    
    private String src;
    
    private String sandbox;
    
    @PropertyGetter("src")
    public String getSrc() {
        return src;
    }
    
    @PropertySetter("src")
    public void setSrc(String src) {
        sync("src", this.src = nullify(src));
    }
    
    public void setContent(MimeContent content) {
        setSrc(content == null ? null : content.getSrc());
    }
    
    @Override
    public void setContent(String content) {
        setContent(content == null ? null : new MimeContent("text/html", content.getBytes()));
    }
    
    @PropertyGetter("sandbox")
    public String getSandbox() {
        return sandbox;
    }
    
    @PropertySetter("sandbox")
    public void setSandbox(String sandbox) {
        if (!areEqual(sandbox, this.sandbox)) {
            sync("sandbox", this.sandbox = sandbox);
        }
    }
    
    @EventHandler(value = "load", syncToClient = false)
    private void _onLoad(LoadEvent event) {
        String src = nullify(event.getSrc());
        
        if (src != null) {
            this.src = src;
        }
    }
}

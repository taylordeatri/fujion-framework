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

import org.fujion.ancillary.MimeContent;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A component representing an embedded image.
 */
@Component(tag = "image", widgetClass = "Image", parentTag = "*")
public class Image extends BaseUIComponent {
    
    private String src;
    
    private String alt;
    
    public Image() {
    }
    
    public Image(String src) {
        setSrc(src);
    }
    
    public Image(String src, String alt) {
        setSrc(src);
        setAlt(alt);
    }
    
    public Image(MimeContent content) {
        setContent(content);
    }
    
    @PropertyGetter("src")
    public String getSrc() {
        return src;
    }
    
    @PropertySetter("src")
    public void setSrc(String src) {
        if (!areEqual(src = nullify(src), this.src)) {
            sync("src", this.src = src);
        }
    }
    
    public void setContent(MimeContent content) {
        setSrc(content == null ? null : content.getSrc());
    }
    
    @PropertyGetter("alt")
    public String getAlt() {
        return alt;
    }
    
    @PropertySetter("alt")
    public void setAlt(String alt) {
        if (!areEqual(alt = nullify(alt), this.alt)) {
            sync("alt", this.alt = alt);
        }
    }
}

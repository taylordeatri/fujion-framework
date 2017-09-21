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
    
    /**
     * Returns the URL of the image resource.
     *
     * @return URL of the image resource.
     */
    @PropertyGetter("src")
    public String getSrc() {
        return src;
    }
    
    /**
     * Sets the URL of the image resource.
     *
     * @param src URL of the image resource.
     */
    @PropertySetter("src")
    public void setSrc(String src) {
        if (!areEqual(src = nullify(src), this.src)) {
            propertyChange("src", this.src, this.src = src, true);
        }
    }
    
    /**
     * Directly sets the image content.
     *
     * @param content The image content.
     */
    public void setContent(MimeContent content) {
        setSrc(content == null ? null : content.getSrc());
    }
    
    /**
     * Returns the alternate text for the image.
     *
     * @return The alternate text for the image.
     */
    @PropertyGetter("alt")
    public String getAlt() {
        return alt;
    }
    
    /**
     * Sets the alternate text for the image.
     *
     * @param alt The alternate text for the image.
     */
    @PropertySetter("alt")
    public void setAlt(String alt) {
        if (!areEqual(alt = nullify(alt), this.alt)) {
            propertyChange("alt", this.alt, this.alt = alt, true);
        }
    }
}

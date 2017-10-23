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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A single pane within a pane view.
 */
@Component(tag = "pane", widgetModule = "fujion-paneview", widgetClass = "Pane", content = ContentHandling.AS_CHILD, parentTag = "paneview", childTag = @ChildTag("*"))
public class Pane extends BaseUIComponent {
    
    private boolean splittable;
    
    private String title;
    
    /**
     * Returns whether the pane displays a splitter. A pane with a splitter can be manually resized.
     *
     * @return If true, the pane has an associated splitter and can be manually resized.
     */
    @PropertyGetter("splittable")
    public boolean isSplittable() {
        return splittable;
    }
    
    /**
     * Sets whether the pane displays a splitter. A pane with a splitter may be manually resized.
     *
     * @param splittable If true, the pane has an associated splitter and can be manually resized.
     */
    @PropertySetter("splittable")
    public void setSplittable(boolean splittable) {
        propertyChange("splittable", this.splittable, this.splittable = splittable, true);
    }
    
    /**
     * Returns the title text.
     *
     * @return The title text.
     */
    @PropertyGetter("title")
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title text.
     *
     * @param title The title text.
     */
    @PropertySetter("title")
    public void setTitle(String title) {
        propertyChange("title", this.title, this.title = nullify(title), true);
    }
    
}

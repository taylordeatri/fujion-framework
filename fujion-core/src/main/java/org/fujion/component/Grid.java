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
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A grid component.
 */
@Component(tag = "grid", widgetModule = "fujion-grid", widgetClass = "Grid", parentTag = "*", childTag = {
        @ChildTag(value = "rows", maximum = 1), @ChildTag(value = "columns", maximum = 1) })
public class Grid extends BaseUIComponent {
    
    private Columns columns;
    
    private Rows rows;
    
    private String title;
    
    public Grid() {
        addClass("table");
    }
    
    /**
     * Updates the rows and columns properties as these are added.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        super.afterAddChild(child);
        
        if (child instanceof Rows) {
            rows = (Rows) child;
        }
        
        if (child instanceof Columns) {
            columns = (Columns) child;
        }
    }
    
    /**
     * Updates the rows and columns properties as these are removed.
     *
     * @see org.fujion.component.BaseUIComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        super.afterRemoveChild(child);
        
        if (child == rows) {
            rows = null;
        } else if (child == columns) {
            columns = null;
        }
    }
    
    /**
     * Returns the Columns child.
     *
     * @return The Columns child (may be null).
     */
    public Columns getColumns() {
        return columns;
    }
    
    /**
     * Returns the Rows child.
     *
     * @return The Rows child (may be null).
     */
    public Rows getRows() {
        return rows;
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

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
 * A component representing a single menu item.
 */
@Component(tag = "menuitem", widgetClass = "Menuitem", parentTag = { "menu", "menupopup", "menuitem" }, childTag = {
        @ChildTag("menuitem"), @ChildTag("menuheader"), @ChildTag("menuseparator") })
public class Menuitem extends BaseMenuComponent {
    
    private boolean checkable;
    
    private boolean checked;
    
    @PropertyGetter("checkable")
    public boolean isCheckable() {
        return checkable;
    }
    
    @PropertySetter("checkable")
    public void setCheckable(boolean checkable) {
        if (checkable != this.checkable) {
            sync("checkable", this.checkable = checkable);
        }
    }
    
    @PropertyGetter("checked")
    public boolean isChecked() {
        return checked;
    }
    
    @PropertySetter("checked")
    public void setChecked(boolean checked) {
        if (checked != this.checked) {
            sync("checked", this.checked = checked);
        }
    }
    
}

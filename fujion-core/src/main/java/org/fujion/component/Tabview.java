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
 * A component supporting a tab-based view.
 */
@Component(tag = "tabview", widgetModule = "fujion-tabview", widgetClass = "Tabview", parentTag = "*", childTag = @ChildTag("tab"))
public class Tabview extends BaseUIComponent {

    /**
     * Placement of tabs in a tab view. Default is top.
     */
    public enum TabPosition {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private Tab selectedTab;

    private TabPosition tabPosition = TabPosition.TOP;

    public Tab getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(Tab selectedTab) {
        validateIsChild(selectedTab);

        if (this.selectedTab != null) {
            this.selectedTab._setSelected(false, false);
        }

        this.selectedTab = selectedTab;

        if (selectedTab != null) {
            selectedTab._setSelected(true, false);
        }
    }

    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (child == selectedTab) {
            selectedTab = null;
        }
    }

    @Override
    protected void afterAddChild(BaseComponent child) {
        if (((Tab) child).isSelected()) {
            setSelectedTab((Tab) child);
        }
    }

    @PropertyGetter("tabPosition")
    public TabPosition getTabPosition() {
        return tabPosition;
    }

    @PropertySetter("tabPosition")
    public void setTabPosition(TabPosition tabPosition) {
        tabPosition = tabPosition == null ? TabPosition.TOP : tabPosition;

        if (tabPosition != this.tabPosition) {
            sync("tabPosition", this.tabPosition = tabPosition);
        }
    }

}

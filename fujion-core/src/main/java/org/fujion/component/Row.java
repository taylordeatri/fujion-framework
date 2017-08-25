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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.EventUtil;

/**
 * A component representing a single row within a grid.
 */
@Component(tag = "row", widgetModule = "fujion-grid", widgetClass = "Row", content = ContentHandling.AS_CHILD, parentTag = "rows", childTag = @ChildTag("*"))
public class Row extends BaseUIComponent {

    private boolean selected;

    @PropertyGetter("selected")
    public boolean isSelected() {
        return selected;
    }

    @PropertySetter("selected")
    public void setSelected(boolean selected) {
        _setSelected(selected, true, true);
    }

    protected void _setSelected(boolean selected, boolean notifyClient, boolean notifyParent) {
        if (selected != this.selected) {
            this.selected = selected;

            if (notifyClient) {
                sync("selected", selected);
            }

            if (notifyParent && getParent() != null) {
                ((Rows) getParent())._updateSelected(this);
            }
        }
    }

    @EventHandler(value = "change", syncToClient = false)
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), false, true);
        event = new ChangeEvent(this.getParent(), event.getData(), this);
        EventUtil.send(event);
    }
}

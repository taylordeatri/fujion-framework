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
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.EventUtil;

/**
 * A single item within a combo box.
 */
@Component(tag = "comboitem", widgetClass = "Comboitem", parentTag = "combobox")
public class Comboitem extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionNone> {

    private boolean selected;

    private String value;

    public Comboitem() {
        super();
    }

    public Comboitem(String label) {
        super(label);
    }

    @PropertyGetter("selected")
    public boolean isSelected() {
        return selected;
    }

    @PropertySetter("selected")
    public void setSelected(boolean selected) {
        _setSelected(selected, true, true);
    }

    @PropertyGetter("value")
    public String getValue() {
        return value;
    }

    @PropertySetter("value")
    public void setValue(String value) {
        if (!areEqual(value, this.value)) {
            sync("value", this.value = value);
        }
    }

    protected void _setSelected(boolean selected, boolean notifyClient, boolean notifyParent) {
        if (selected != this.selected) {
            this.selected = selected;

            if (notifyClient) {
                sync("selected", selected);
            }

            if (notifyParent && getParent() != null) {
                getCombobox()._updateSelected(selected ? this : null);
            }
        }
    }

    public Combobox getCombobox() {
        return (Combobox) getParent();
    }

    @EventHandler(value = "change", syncToClient = false)
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), false, true);
        event = new ChangeEvent(this.getParent(), event.getData(), this);
        EventUtil.send(event);
    }
    
}

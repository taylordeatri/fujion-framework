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
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;

/**
 * A component representing a simple check box with associated label.
 */
@Component(tag = "checkbox", widgetClass = "Checkbox", parentTag = "*")
public class Checkbox extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionHorz> {

    private boolean checked;

    public Checkbox() {
        this(null);
    }

    public Checkbox(String label) {
        super(label);
        setPosition(LabelPositionHorz.RIGHT);
    }

    /**
     * Returns the checked state of the check box.
     *
     * @return True if the check box is checked, false if not.
     */
    @PropertyGetter("checked")
    public boolean isChecked() {
        return checked;
    }

    /**
     * Sets the checked state of the check box.
     *
     * @param checked if the check box is checked, false if not.
     */
    @PropertySetter("checked")
    public void setChecked(boolean checked) {
        propertyChange("checked", this.checked, this.checked = checked, true);
    }

    /**
     * Returns the position of the label relative to the contained elements. Defaults to 'left'.
     *
     * @return May be one of: left, right.
     */
    @Override
    @PropertyGetter("position")
    public LabelPositionHorz getPosition() {
        return super.getPosition();
    }

    /**
     * Sets the position of the label relative to the contained elements.
     *
     * @param position May be one of: left, right.
     */
    @Override
    @PropertySetter("position")
    public void setPosition(LabelPositionHorz position) {
        super.setPosition(position);
    }

    /**
     * Handler for change events sent from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false)
    protected void _onChange(ChangeEvent event) {
        checked = defaultify(event.getValue(Boolean.class), true);
    }

}

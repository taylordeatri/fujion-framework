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
import org.springframework.util.Assert;

/**
 * A slider component.
 */
@Component(tag = "slider", widgetClass = "Slider", parentTag = "*")
public class Slider extends BaseUIComponent {

    /**
     * Orientation of slider component.
     */
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
    
    private Orientation orientation = Orientation.HORIZONTAL;
    
    private int value;
    
    private int maxvalue = 100;
    
    private int minvalue;

    private int step = 1;

    private boolean synced;

    @PropertyGetter("value")
    public int getValue() {
        return value;
    }
    
    @PropertySetter("value")
    public void setValue(int value) {
        if (value != this.value) {
            sync("value", this.value = value);
        }
    }
    
    @PropertyGetter("maxvalue")
    public int getMaxValue() {
        return maxvalue;
    }
    
    @PropertySetter("maxvalue")
    public void setMaxValue(int maxvalue) {
        if (maxvalue != this.maxvalue) {
            sync("maxvalue", this.maxvalue = maxvalue);
        }
    }
    
    @PropertyGetter("minvalue")
    public int getMinValue() {
        return minvalue;
    }

    @PropertySetter("minvalue")
    public void setMinValue(int minvalue) {
        if (minvalue != this.minvalue) {
            sync("minvalue", this.minvalue = minvalue);
        }
    }
    
    @PropertyGetter("orientation")
    public Orientation getOrientation() {
        return orientation;
    }
    
    @PropertySetter("orientation")
    public void setOrientation(Orientation orientation) {
        orientation = orientation == null ? Orientation.HORIZONTAL : orientation;

        if (orientation != this.orientation) {
            sync("orientation", this.orientation = orientation);
        }
    }
    
    @PropertyGetter("step")
    public int getStep() {
        return step;
    }
    
    @PropertySetter("step")
    public void setStep(int step) {
        Assert.isTrue(step > 0, "Step value must be greater than zero.");

        if (step != this.step) {
            sync("step", this.step = step);
        }
    }

    @PropertyGetter("synchronized")
    public boolean getSynchronized() {
        return synced;
    }

    @PropertySetter("synchronized")
    protected void setSynchronized(boolean synced) {
        if (synced != this.synced) {
            sync("synced", this.synced = synced);
        }
    }

    @EventHandler(value = "change", syncToClient = false)
    private void _onChange(ChangeEvent event) {
        value = defaultify(event.getValue(Integer.class), value);
    }
}

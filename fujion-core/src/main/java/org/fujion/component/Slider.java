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
        /**
         * The slider is oriented horizontally.
         */
        HORIZONTAL,
        /**
         * The slider is oriented vertically.
         */
        VERTICAL
    }
    
    private Orientation orientation = Orientation.HORIZONTAL;
    
    private int value;
    
    private int maxvalue = 100;
    
    private int minvalue;

    private int step = 1;

    private boolean synced;

    /**
     * Returns the current value of the slider.
     *
     * @return The current value of the slider.
     */
    @PropertyGetter("value")
    public int getValue() {
        return value;
    }
    
    /**
     * Sets the current value of the slider.
     *
     * @param value The current value of the slider.
     */
    @PropertySetter("value")
    public void setValue(int value) {
        propertyChange("value", this.value, this.value = value, true);
    }
    
    /**
     * Returns the maximum allowable value. Defaults to 100.
     *
     * @return The maximum allowable value. Defaults to 100.
     */
    @PropertyGetter("maxvalue")
    public int getMaxValue() {
        return maxvalue;
    }
    
    /**
     * Sets the maximum allowable value.
     *
     * @param maxvalue The maximum allowable value.
     */
    @PropertySetter("maxvalue")
    public void setMaxValue(int maxvalue) {
        propertyChange("maxvalue", this.maxvalue, this.maxvalue = maxvalue, true);
    }
    
    /**
     * Returns the minimum allowable value. Defaults to 0.
     *
     * @return The minimum allowable value. Defaults to 0.
     */
    @PropertyGetter("minvalue")
    public int getMinValue() {
        return minvalue;
    }

    /**
     * Sets the minimum allowable value.
     *
     * @param minvalue The minimum allowable value.
     */
    @PropertySetter("minvalue")
    public void setMinValue(int minvalue) {
        propertyChange("minvalue", this.minvalue, this.minvalue = minvalue, true);
    }
    
    /**
     * Returns the {@link Orientation orientation} of the component.
     *
     * @return The {@link Orientation orientation} of the component.
     */
    @PropertyGetter("orientation")
    public Orientation getOrientation() {
        return orientation;
    }
    
    /**
     * Sets the {@link Orientation orientation} of the component.
     *
     * @param orientation The {@link Orientation orientation} of the component.
     */
    @PropertySetter("orientation")
    public void setOrientation(Orientation orientation) {
        propertyChange("orientation", this.orientation, this.orientation = defaultify(orientation, Orientation.HORIZONTAL),
            true);
    }
    
    /**
     * Returns the amount of change in the current value when an arrow button is clicked. Default is
     * 1.
     *
     * @return The amount of change in the current value when an arrow button is clicked. Default is
     *         1.
     */
    @PropertyGetter("step")
    public int getStep() {
        return step;
    }
    
    /**
     * Sets the amount of change in the current value when an arrow button is clicked.
     *
     * @param step The amount of change in the current value when an arrow button is clicked.
     */
    @PropertySetter("step")
    public void setStep(int step) {
        Assert.isTrue(step > 0, "Step value must be greater than zero.");
        propertyChange("step", this.step, this.step = step, true);
    }

    /**
     * Returns the synchronized setting. If set to true, every change to the slider's value will be
     * sent to the server. If false, only the final value will be sent.
     *
     * @return The synchronized setting.
     */
    @PropertyGetter("synchronized")
    public boolean getSynchronized() {
        return synced;
    }

    /**
     * Sets the synchronized setting. If set to true, every change to the slider's value will be
     * sent to the server. If false, only the final value will be sent.
     *
     * @param synced The synchronized setting.
     */
    @PropertySetter("synchronized")
    protected void setSynchronized(boolean synced) {
        propertyChange("synced", this.synced, this.synced = synced, true);
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false)
    private void _onChange(ChangeEvent event) {
        value = defaultify(event.getValue(Integer.class), value);
    }
}

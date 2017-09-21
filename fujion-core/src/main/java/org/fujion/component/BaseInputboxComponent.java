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

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.NumUtil;

/**
 * The base class for all input box components.
 *
 * @param <T> The value type for the input box.
 */
public abstract class BaseInputboxComponent<T> extends BaseInputComponent<T> {
    
    private T minvalue;
    
    private T maxvalue;
    
    private String pattern;
    
    private String placeholder;
    
    private int maxLength;
    
    private boolean readonly;
    
    private boolean required;
    
    private boolean synced;
    
    /**
     * Returns the synchronization flag. A true value means that the client will notify the server
     * as the value of the input box changes. A false value means that the client will notify the
     * server of the new value only when the input element loses focus.
     *
     * @return The synchronization flag.
     */
    protected boolean getSynchronized() {
        return synced;
    }
    
    /**
     * Sets the synchronization flag. A true value means that the client will notify the server as
     * the value of the input box changes. A false value means that the client will notify the
     * server of the new value only when the input element loses focus.
     *
     * @param synchronize The synchronization flag.
     */
    protected void setSynchronized(boolean synchronize) {
        if (synchronize != this.synced) {
            propertyChange("synced", this.synced, this.synced = synchronize, true);
        }
    }
    
    /**
     * Returns the minimum allowable value, if any.
     *
     * @return The minimum allowable value. Null indicates no minimum.
     */
    @PropertyGetter("minvalue")
    public T getMinValue() {
        return minvalue;
    }
    
    @PropertySetter("minvalue")
    private void _setMinValue(String minvalue) {
        setMinValue(_toValue(minvalue));
    }
    
    /**
     * Sets the minimum allowable value.
     *
     * @param minvalue The minimum allowable value. Null indicates no minimum.
     */
    public void setMinValue(T minvalue) {
        if (!areEqual(minvalue, this.minvalue)) {
            _sync("minvalue", _toString(minvalue));
            propertyChange("minvalue", this.minvalue, this.minvalue = minvalue, false);
        }
    }
    
    /**
     * Returns the maximum allowable value, if any.
     *
     * @return The maximum allowable value. Null indicates no maximum.
     */
    @PropertyGetter("maxvalue")
    public T getMaxValue() {
        return maxvalue;
    }
    
    @PropertySetter("maxvalue")
    private void _setMaxValue(String maxvalue) {
        setMaxValue(_toValue(maxvalue));
    }
    
    /**
     * Sets the maximum allowable value.
     *
     * @param maxvalue The maximum allowable value. Null indicates no maximum.
     */
    public void setMaxValue(T maxvalue) {
        if (!areEqual(maxvalue, this.maxvalue)) {
            _sync("maxvalue", _toString(maxvalue));
            propertyChange("maxvalue", this.maxvalue, this.maxvalue = maxvalue, false);
        }
    }
    
    /**
     * Returns the regular expression that constrains the input format.
     *
     * @return Regular expression that constrains the input format.
     */
    @PropertyGetter("pattern")
    public String getPattern() {
        return pattern;
    }
    
    /**
     * Sets the regular expression that constrains the input format.
     *
     * @param pattern Regular expression that constrains the input format.
     */
    @PropertySetter("pattern")
    public void setPattern(String pattern) {
        if (!areEqual(pattern = nullify(pattern), this.pattern)) {
            propertyChange("pattern", this.pattern, this.pattern = pattern, true);
        }
    }
    
    /**
     * Returns the placeholder message that is displayed when the input box is empty.
     *
     * @return The placeholder message.
     */
    @PropertyGetter("placeholder")
    public String getPlaceholder() {
        return placeholder;
    }
    
    /**
     * Sets the placeholder message that is displayed when the input box is empty.
     *
     * @param placeholder The placeholder message.
     */
    @PropertySetter("placeholder")
    public void setPlaceholder(String placeholder) {
        if (!areEqual(placeholder = nullify(placeholder), this.placeholder)) {
            propertyChange("placeholder", this.placeholder, this.placeholder = placeholder, true);
        }
    }
    
    /**
     * Returns the maximum character length of input.
     *
     * @return The maximum character length of input.
     */
    @PropertyGetter("maxlength")
    public int getMaxLength() {
        return maxLength;
    }
    
    /**
     * Sets the maximum character length of input.
     *
     * @param maxLength The maximum character length of input.
     */
    @PropertySetter("maxlength")
    public void setMaxLength(int maxLength) {
        maxLength = NumUtil.enforceRange(maxLength, 0, 524288);
        
        if (maxLength != this.maxLength) {
            propertyChange("maxlength", this.maxLength, this.maxLength = maxLength, true);
        }
    }
    
    /**
     * Returns true if the input box is read-only.
     *
     * @return True if the input box is read-only.
     */
    @PropertyGetter("readonly")
    public boolean isReadonly() {
        return readonly;
    }
    
    /**
     * Sets the read-only state of the input box.
     *
     * @param readonly If true, the contents of the input box may not be changed by the user.
     */
    @PropertySetter("readonly")
    public void setReadonly(boolean readonly) {
        if (readonly != this.readonly) {
            propertyChange("readonly", this.readonly, this.readonly = readonly, true);
        }
    }
    
    /**
     * Returns true if input is required for this component.
     *
     * @return True if input is required for this component.
     */
    @PropertyGetter("required")
    public boolean isRequired() {
        return required;
    }
    
    /**
     * Sets the required state of the input box.
     *
     * @param required True if input is required for this component.
     */
    @PropertySetter("required")
    public void setRequired(boolean required) {
        if (required != this.required) {
            propertyChange("required", this.required, this.required = required, true);
        }
    }
    
    /**
     * Selects the entire contents of the input box.
     */
    public void selectAll() {
        invoke("selectAll");
    }
    
    /**
     * Selects a range of characters in the input box.
     *
     * @param start Start of range.
     * @param end End of range.
     */
    public void selectRange(int start, int end) {
        invoke("selectRange", start, end);
    }
}

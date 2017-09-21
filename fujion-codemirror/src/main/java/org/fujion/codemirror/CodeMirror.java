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
package org.fujion.codemirror;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseInputComponent;

/**
 * Fujion wrapper for CodeMirror JavaScript editor.
 */
@Component(tag = "codemirror", widgetModule = "fujion-codemirror", widgetClass = "CodeMirror", parentTag = "*")
public class CodeMirror extends BaseInputComponent<String> {
    
    private String mode;
    
    private boolean lineNumbers;
    
    private String placeholder;
    
    private boolean readonly;
    
    /**
     * Invokes the CodeMirror format method.
     */
    public void format() {
        invoke("format");
    }
    
    /**
     * Returns true if the editor is set to read-only.
     *
     * @return True if read-only.
     */
    @PropertyGetter("readonly")
    public boolean isReadonly() {
        return readonly;
    }
    
    /**
     * Set the read-only state of the editor.
     *
     * @param readonly The read-only state.
     */
    @PropertySetter("readonly")
    public void setReadonly(boolean readonly) {
        if (readonly != this.readonly) {
            propertyChange("readonly", this.readonly, this.readonly = readonly, true);
        }
    }
    
    /**
     * Returns the placeholder value.
     *
     * @return The placeholder value.
     */
    @PropertyGetter("placeholder")
    public String getPlaceholder() {
        return placeholder;
    }
    
    /**
     * Sets the placeholder value.
     *
     * @param placeholder The placeholder value.
     */
    @PropertySetter("placeholder")
    public void setPlaceholder(String placeholder) {
        if (!areEqual(placeholder = nullify(placeholder), this.placeholder)) {
            propertyChange("placeholder", this.placeholder, this.placeholder = placeholder, true);
        }
    }
    
    /**
     * Returns the CodeMirror mode parameter.
     *
     * @return The CodeMirror mode parameter.
     */
    @PropertyGetter("mode")
    public String getMode() {
        return mode;
    }
    
    /**
     * Sets the CodeMirror mode parameter.
     *
     * @param mode The CodeMirror mode parameter.
     */
    @PropertySetter("mode")
    public void setMode(String mode) {
        if (!areEqual(mode = trimify(mode), this.mode)) {
            propertyChange("mode", this.mode, this.mode = mode, true);
        }
    }
    
    /**
     * Returns the CodeMirror lineNumbers parameter.
     *
     * @return The CodeMirror lineNumbers parameter.
     */
    @PropertyGetter("lineNumbers")
    public boolean getLineNumbers() {
        return lineNumbers;
    }
    
    /**
     * Sets the CodeMirror lineNumbers parameter.
     *
     * @param lineNumbers The CodeMirror lineNumbers parameter.
     */
    @PropertySetter("lineNumbers")
    public void setLineNumbers(boolean lineNumbers) {
        if (lineNumbers != this.lineNumbers) {
            propertyChange("lineNumbers", this.lineNumbers, this.lineNumbers = lineNumbers, true);
        }
    }
    
    @Override
    protected String _toValue(String value) {
        return value;
    }
    
    @Override
    protected String _toString(String value) {
        return value;
    }
}

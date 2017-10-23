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
 * Associates a label with another component.
 */
@Component(tag = "caption", widgetClass = "Caption", parentTag = "*", childTag = @ChildTag("*"))
public class Caption extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionAll> {
    
    /**
     * Alignment of label relative to child component(s).
     */
    public enum LabelAlignment {
        START, CENTER, END
    }
    
    private LabelAlignment alignment = LabelAlignment.START;
    
    private String labelStyle;
    
    private String labelClass = "label-default";
    
    public Caption() {
        setPosition(LabelPositionAll.LEFT);
    }
    
    /**
     * Returns the position of the label relative to its associated component. Defaults to 'left'.
     *
     * @return May be one of: left, right, top, or bottom.
     */
    @Override
    @PropertyGetter("position")
    public LabelPositionAll getPosition() {
        return super.getPosition();
    }
    
    /**
     * Sets the position of the label relative to its associated component.
     *
     * @param position May be one of: left, right, top, or bottom.
     */
    @Override
    @PropertySetter("position")
    public void setPosition(LabelPositionAll position) {
        super.setPosition(position);
    }
    
    /**
     * Returns the alignment of the label. Defaults to 'start'.
     *
     * @return May be one of start, center, end.
     */
    @PropertyGetter("alignment")
    public LabelAlignment getAlignment() {
        return alignment;
    }
    
    /**
     * Sets the alignment of the label.
     *
     * @param alignment May be one of: start, center, end.
     */
    @PropertySetter("alignment")
    public void setAlignment(LabelAlignment alignment) {
        propertyChange("alignment", this.alignment, this.alignment = defaultify(alignment, LabelAlignment.START), true);
    }
    
    /**
     * Returns the style(s) associated with the label.
     *
     * @return The label style(s).
     */
    public String getLabelStyle() {
        return labelStyle;
    }
    
    /**
     * Sets the style(s) of the label.
     *
     * @param labelStyle The label style(s).
     */
    public void setLabelStyle(String labelStyle) {
        propertyChange("labelStyle", this.labelStyle, this.labelStyle = trimify(labelStyle), true);
    }
    
    /**
     * Returns the CSS class(es) associated with the label.
     *
     * @return The label CSS class(es).
     */
    public String getLabelClass() {
        return labelClass;
    }
    
    /**
     * Sets the CSS class(es) of the label.
     *
     * @param labelClass The label CSS class(es).
     */
    public void setLabelClass(String labelClass) {
        propertyChange("labelClass", this.labelClass, this.labelClass = trimify(labelClass), true);
    }
    
}

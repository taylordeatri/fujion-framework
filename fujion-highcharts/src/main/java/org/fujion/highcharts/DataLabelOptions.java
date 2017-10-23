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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.JavaScript;

/**
 * Options for data labels.
 */
public class DataLabelOptions extends Options {
    
    /**
     * The alignment of the data label compared to the point. Can be one of "left", "center" or
     * "right". Defaults to "center".
     */
    public AlignHorizontal align;
    
    /**
     * The background color or gradient for the data label. Defaults to undefined.
     */
    public String backgroundColor;
    
    /**
     * The border color for the data label. Defaults to undefined.
     */
    public String borderColor;
    
    /**
     * The border radius in pixels for the data label. Defaults to 0.
     */
    public Integer borderRadius;
    
    /**
     * The border width in pixels for the data label. Defaults to 0.
     */
    public Integer borderWidth;
    
    /**
     * The text color for the data labels. Defaults to null.
     */
    public String color;
    
    /**
     * Enable or disable the data labels. Defaults to false.
     */
    public Boolean enabled;
    
    /**
     * Callback JavaScript function to format the data label.
     */
    @JavaScript
    public String formatter;
    
    /**
     * How to handle overflowing labels on horizontal axis. Can be undefined or "justify". If
     * "justify", labels will not render outside the plot area. If there is room to move it, it will
     * be aligned to the edge, else it will be removed. Defaults to undefined.
     */
    public String overflow;
    
    /**
     * When either the borderWidth or the backgroundColor is set, this is the padding within the
     * box. Defaults to 2. Defaults to 2.
     */
    public Integer padding;
    
    /**
     * Rotation of the labels in degrees. Defaults to 0.
     */
    public Integer rotation;
    
    /**
     * The shadow of the box. Works best with borderWidth or backgroundColor. The shadow can be an
     * object configuration containing color, offsetX, offsetY, opacity and width. Defaults to
     * false.
     */
    public Object shadow;
    
    /**
     * CSS styles for the label.
     */
    public final StyleOptions style = new StyleOptions();
    
    /**
     * The x position offset of the label relative to the point. Defaults to 0.
     */
    public Integer x;
    
    /**
     * The y position offset of the label relative to the point. Defaults to -6.
     */
    public Integer y;
}

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

/**
 * Options for text labels for plot bands or plot lines.
 */
public class PlotLabelOptions extends Options {
    
    /**
     * Horizontal alignment of the label. Can be one of "left", "center" or "right". Defaults to
     * "center".
     */
    public AlignHorizontal align;
    
    /**
     * Rotation of the text label in degrees . Defaults to 0.
     */
    public Integer rotation;
    
    /**
     * CSS styles for the text label.
     */
    public final StyleOptions style = new StyleOptions();
    
    /**
     * The string text itself. A subset of HTML is supported.
     */
    public String text;
    
    /**
     * The text alignment for the label. While align determines where the texts anchor point is
     * placed within the plot band, textAlign determines how the text is aligned against its anchor
     * point. Possible values are "left", "center" and "right". Defaults to the same as the align
     * option.
     */
    public AlignHorizontal textAlign;
    
    /**
     * Vertical alignment of the label relative to the plot band. Can be one of "top", "middle" or
     * "bottom". Defaults to "top".
     */
    public AlignVertical verticalAlign;
    
    /**
     * Horizontal position relative the alignment. Default varies by orientation.
     */
    public Integer x;
    
    /**
     * Vertical position of the text baseline relative to the alignment. Default varies by
     * orientation.
     */
    public Integer y;
}

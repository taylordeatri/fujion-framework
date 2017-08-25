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
package org.fujion.highcharts;

/**
 * Plot options for bar series.
 */
public class PlotBar extends PlotOptions {
    
    /**
     * The color of the border surrounding each column or bar. Defaults to "#FFFFFF".
     */
    public String borderColor;
    
    /**
     * The corner radius of the border surrounding each column or bar. Defaults to 0.
     */
    public Integer borderRadius;
    
    /**
     * The width of the border surrounding each column or bar. Defaults to 1.
     */
    public Integer borderWidth;
    
    /**
     * Padding between each value groups, in x axis units. Defaults to 0.2.
     */
    public Double groupPadding;
    
    /**
     * Whether to group non-stacked columns or to let them render independent of each other.
     * Non-grouped columns will be laid out individually and overlap each other. Defaults to true.
     */
    public Boolean grouping;
    
    /**
     * The minimal height for a column or width for a bar. By default, 0 values are not shown. To
     * visualize a 0 (or close to zero) point, set the minimal point length to a pixel value like 3.
     * In stacked column charts, minPointLength might not be respected for tightly packed values.
     * Defaults to 0.
     */
    public Integer minPointLength;
    
    /**
     * Padding between each column or bar, in x axis units. Defaults to 0.1.
     */
    public Double pointPadding;
    
    /**
     * The X axis range that each point is valid for. This determines the width of the column. On a
     * categorized axis, the range will be 1 by default (one category unit). On linear and datetime
     * axes, the range will be computed as the distance between the two closest data points.
     */
    public Double pointRange;
    
    /**
     * A pixel value specifying a fixed width for each column or bar. When null, the width is
     * calculated from the pointPadding and groupPadding. Defaults to null.
     */
    public Integer pointWidth;
    
}

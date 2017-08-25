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
 * Plot options for funnel plot.
 */
public class PlotFunnel extends PlotPie {
    
    /**
     * The height of the neck, the lower part of a funnel. If it is a number it defines the pixel
     * height, if it is a percentage string it is the percentage of the plot area height.
     */
    public String height;
    
    /**
     * Equivalent to chart.ignoreHiddenSeries, this option tells whether the series shall be redrawn
     * as if the hidden point were null. The default value is true.
     */
    public Boolean ignoreHiddenPoint;
    
    /**
     * The height of the neck, the lower part of the funnel. A number defines pixel width, a
     * percentage string defines a percentage of the plot area height. Defaults to 25%.
     */
    public String neckHeight;
    
    /**
     * The width of the neck, the lower part of the funnel. A number defines pixel width, a
     * percentage string defines a percentage of the plot area width.
     */
    public String neckWidth;
    
}

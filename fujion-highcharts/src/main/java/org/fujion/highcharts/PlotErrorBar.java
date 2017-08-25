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
 * Plot options for plot error bar.
 */
public class PlotErrorBar extends PlotBar {
    
    /**
     * The color of the stem, the vertical line extending from the box to the whiskers. If null, the
     * series color is used. Defaults to null.
     */
    public String stemColor;
    
    /**
     * The dash style of the stem, the vertical line extending from the box to the whiskers.
     * Defaults to Solid.
     */
    public DashStyle stemDashStyle;
    
    /**
     * The width of the stem, the vertical line extending from the box to the whiskers. If null, the
     * width is inherited from the lineWidth option. Defaults to null.
     */
    public Integer stemWidth;
    
    /**
     * The color of the whiskers, the horizontal lines marking low and high values. When null, the
     * general series color is used. Defaults to null.
     */
    public String whiskerColor;
    
    /**
     * The length of the whiskers, the horizontal lines marking low and high values. It can be a
     * numerical pixel value, or a percentage value of the box width. Set 0 to disable whiskers.
     * Defaults to 50%.
     */
    public String whiskerLength;
    
    /**
     * The line width of the whiskers, the horizontal lines marking low and high values. When null,
     * the general lineWidth applies. Defaults to null.
     */
    public Integer whiskerWidth;
}

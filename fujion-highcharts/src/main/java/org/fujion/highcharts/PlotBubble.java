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

/**
 * Plot options for bubble plot.
 */
public class PlotBubble extends PlotOptions {
    
    /**
     * Whether to display negative sized bubbles. The threshold is given by the zThreshold option,
     * and negative bubbles can be visualized by setting negativeColor. Defaults to true.
     */
    public Boolean displayNegative;
    
    /**
     * Maximum bubble size. Bubbles will automatically size between the minSize and maxSize to
     * reflect the z value of each bubble. Can be either pixels (when no unit is given), or a
     * percentage of the smallest one of the plot width and height. Defaults to 20%.
     */
    public String maxSize;
    
    /**
     * Minimum bubble size. Bubbles will automatically size between the minSize and maxSize to
     * reflect the z value of each bubble. Can be either pixels (when no unit is given), or a
     * percentage of the smallest one of the plot width and height. Defaults to 8.
     */
    public String minSize;
    
    /**
     * Whether the bubble's value should be represented by the area or the width of the bubble. The
     * default, area, corresponds best to the human perception of the size of each bubble. Defaults
     * to area.
     */
    public String sizeBy;
    
    /**
     * When displayNegative is false, bubbles with lower Z values are skipped. When displayNegative
     * is true and a negativeColor is given, points with lower Z is colored. Defaults to 0.
     */
    public Double zThreshold;
}

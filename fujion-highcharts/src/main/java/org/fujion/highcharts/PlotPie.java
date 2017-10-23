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
 * Plot options for pie series.
 */
public class PlotPie extends PlotOptions {
    
    /**
     * The color of the border surronding each slice. Defaults to "#FFFFFF".
     */
    public String borderColor;
    
    /**
     * The width of the border surronding each slice. Defaults to 1.
     */
    public Integer borderWidth;
    
    /**
     * The center of the pie chart relative to the plot area. Can be percentages or pixel values.
     * Defaults to ['50%', '50%'].
     */
    public String[] center;
    
    /**
     * The size of the inner diameter for the pie. A size greater than 0 renders a doughnut chart.
     * Can be a percentage or pixel value. Percentages are relative to the size of the plot area.
     * Pixel values are given as integers. Defaults to 0.
     */
    public String innerSize;
    
    /**
     * The minimum size for a pie in response to auto margins. The pie will try to shrink to make
     * room for data labels in side the plot area, but only to this size. Defaults to 80.
     */
    public Integer minSize;
    
    /**
     * The diameter of the pie relative to the plot area. Can be a percentage or pixel value. Pixel
     * values are given as integers. Defaults to "75%".
     */
    public String size;
    
    /**
     * If a point is sliced, moved out from the center, how many pixels should it be moved?.
     * Defaults to 10.
     */
    public Integer slicedOffset;
}

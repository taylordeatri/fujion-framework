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
 * A colored band stretching across the plot area marking an interval on the axis.
 */
public class PlotBandOptions extends Options {
    
    /**
     * The color of the plot band.
     */
    public String color;
    
    /**
     * The linear gradient that defines the line which defines the direction of the gradient.
     */
    public final LinearGradient color_linearGradient = new LinearGradient();
    
    /**
     * The radial gradient that defines the line which defines the direction of the gradient.
     */
    public final RadialGradient color_radialGradient = new RadialGradient();
    
    /**
     * The "stops" that define where the color transitions happen in the linear gradient. The first
     * element is a value between 0 and 1 that define where along the line the transition happens
     * (like a percentage) and the second element is the actual color (#FFBBAA for example).
     */
    public String[][] color_stops;
    
    /**
     * The start position of the plot band in axis units. Defaults to null.
     */
    public Double from;
    
    /**
     * An id used for identifying the plot band in Axis.removePlotBand. Defaults to null.
     */
    public String id;
    
    /**
     * Text labels for the plot bands.
     */
    public final PlotLabelOptions label = new PlotLabelOptions();
    
    /**
     * The end position of the plot band in axis units. Defaults to null.
     */
    public Double to;
    
    /**
     * The z index of the plot band within the chart. Defaults to null.
     */
    public Integer zIndex;
}

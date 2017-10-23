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
 * Plot options for gauge plot.
 */
public class PlotSolidGauge extends PlotOptions {
    
    /**
     * Allow the dial to overshoot the end of the perimeter axis by this many degrees. Say if the
     * gauge axis goes from 0 to 60, a value of 100, or 1000, will show 5 degrees beyond the end of
     * the axis. Defaults to 0.
     */
    public Double overshoot;

    /**
     * When this option is true, the dial will wrap around the axes. For instance, in a full-range
     * gauge going from 0 to 360, a value of 400 will point to 40. When wrap is false, the dial
     * stops at 360. Defaults to true.
     */
    public Boolean wrap;
    
}

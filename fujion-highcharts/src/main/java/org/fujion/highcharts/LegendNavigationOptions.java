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
 * Options for the paging or navigation appearing when the legend overflows.
 */
public class LegendNavigationOptions extends Options {
    
    /**
     * The color for the active up or down arrow in the legend page navigation. Defaults to #3E576F.
     */
    public String activeColor;
    
    /**
     * How to animate the pages when navigating up or down. A value of true applies the default
     * navigation given in the chart.animation option. Additional options can be given as an object
     * containing values for easing and duration. Defaults to true.
     */
    public Boolean animation;
    
    /**
     * The pixel size of the up and down arrows in the legend paging navigation. Defaults to 12.
     */
    public Integer arrowSize;
    
    /**
     * The color of the inactive up or down arrow in the legend page navigation. Defaults to #CCC.
     */
    public String inactiveColor;
    
    /**
     * Text styles for the legend page navigation.
     */
    public final StyleOptions style = new StyleOptions();
}

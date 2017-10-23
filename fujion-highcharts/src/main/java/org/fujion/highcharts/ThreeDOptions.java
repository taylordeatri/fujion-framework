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
 * Options for 3D presentation.
 */
public class ThreeDOptions extends Options {

    /**
     * One of the two rotation angles for the chart. Defaults to 0.
     */
    public Double alpha;

    /**
     * One of the two rotation angles for the chart. Defaults to 0.
     */
    public Double beta;

    /**
     * The total depth of the chart. Defaults to 100.
     */
    public Double depth;

    /**
     * Whether to render the chart using the 3D functionality. Defaults to false.
     */
    public Boolean enabled;

    /**
     * Defines the back panel of the frame around 3D charts.
     */
    public final ThreeDFrameOptions frame_back = new ThreeDFrameOptions();
    
    /**
     * The bottom of the frame around a 3D chart.
     */
    public final ThreeDFrameOptions frame_bottom = new ThreeDFrameOptions();
    
    /**
     * The side for the frame around a 3D chart.
     */
    public final ThreeDFrameOptions frame_side = new ThreeDFrameOptions();
    
    /**
     * Defines the distance the viewer is standing in front of the chart, this setting is important
     * to calculate the perspective effect in column and scatter charts. It is not used for 3D pie
     * charts. Defaults to 100.
     */
    public Double viewDistance;
    
}

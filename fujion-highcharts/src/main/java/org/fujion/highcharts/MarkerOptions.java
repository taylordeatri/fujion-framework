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

import org.fujion.ancillary.Options;
import org.fujion.ancillary.OptionMap;

/**
 * Options for point markers.
 */
public class MarkerOptions extends Options {
    
    /**
     * Enable or disable the point marker. Defaults to true.
     */
    public Boolean enabled;
    
    /**
     * he fill color of the point marker. When null, the series' or point's color is used. Defaults
     * to null.
     */
    public String fillColor;
    
    /**
     * The color of the point marker's outline. When null, the series' or point's color is used.
     * Defaults to "#FFFFFF".
     */
    public String lineColor;
    
    /**
     * The width of the point marker's outline. Defaults to 0.
     */
    public Integer lineWidth;
    
    /**
     * The radius of the point marker. Defaults to 0.
     */
    public Integer radius;
    
    /**
     * Marker states for hover.
     */
    public transient final MarkerStateOptions hover_state = new MarkerStateOptions();
    
    /**
     * Marker states for select.
     */
    public transient final MarkerStateOptions select_state = new MarkerStateOptions();
    
    /**
     * Hover and select states.
     */
    public final OptionMap states = new OptionMap();
    
    /**
     * A predefined shape or symbol for the marker. When null, the symbol is pulled from
     * options.symbols. Other possible values are "circle", "square", "diamond", "triangle" and
     * "triangle-down". Additionally, the URL to a graphic can be given on this form:
     * "url(graphic.png)". Defaults to null.
     */
    public String symbol;
    
    @Override
    public OptionMap toMap() {
        OptionMap map = super.toMap();
        states.clear();
        map.put("hover", hover_state.toMap());
        map.put("select", select_state.toMap());
        return map;
    }
}

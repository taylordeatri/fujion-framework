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

import java.util.HashMap;
import java.util.Map;

import org.fujion.annotation.ComponentScanner;

/**
 * Static utility methods.
 */
public class Util {
    
    private static final Map<String, Class<? extends PlotOptions>> plotTypes = new HashMap<String, Class<? extends PlotOptions>>();
    
    /**
     * Returns the plot type from its text identifier.
     *
     * @param type The text identifier for the plot type.
     * @return An instance of the specified plot type.
     */
    public static PlotOptions getPlotType(String type) {
        try {
            return plotTypes.get(type).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid plot type: " + type);
        }
    }
    
    /**
     * Load time initializations.
     */
    public static void init() {
        ComponentScanner.getInstance().scanPackage(Util.class.getPackage());
        plotTypes.put("area", PlotArea.class);
        plotTypes.put("arearange", PlotAreaRange.class);
        plotTypes.put("areaspline", PlotAreaSpline.class);
        plotTypes.put("areasplinerange", PlotAreaSplineRange.class);
        plotTypes.put("bar", PlotBar.class);
        plotTypes.put("boxplot", PlotBox.class);
        plotTypes.put("bubble", PlotBubble.class);
        plotTypes.put("column", PlotColumn.class);
        plotTypes.put("columnrange", PlotColumnRange.class);
        plotTypes.put("errorbar", PlotErrorBar.class);
        plotTypes.put("funnel", PlotFunnel.class);
        plotTypes.put("gauge", PlotGauge.class);
        plotTypes.put("line", PlotLine.class);
        plotTypes.put("pie", PlotPie.class);
        plotTypes.put("scatter", PlotScatter.class);
        plotTypes.put("solidgauge", PlotSolidGauge.class);
        plotTypes.put("spline", PlotSpline.class);
        plotTypes.put("waterfall", PlotWaterfall.class);
    }
    
    /**
     * Enforce static class.
     */
    private Util() {
    }
    
}

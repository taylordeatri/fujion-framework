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
 * Plot options for gauge plot.
 */
public class PlotGauge extends PlotOptions {
    
    /**
     * Options for the dial or arrow pointer of the gauge.
     */
    public final DialOptions dial = new DialOptions();
    
    /**
     * Options for the pivot or the center point of the gauge.
     */
    public final PivotOptions pivot = new PivotOptions();
    
}

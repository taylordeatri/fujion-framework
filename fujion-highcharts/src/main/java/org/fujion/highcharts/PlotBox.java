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
 * Plot options for box plot.
 */
public class PlotBox extends PlotBar {
    
    /**
     * The fill color of the box. Defaults to #FFFFFF.
     */
    public String fillColor;
    
    /**
     * The color of the median line. If null, the general series color applies. Defaults to null.
     */
    public String medianColor;
    
    /**
     * The pixel width of the median line. If null, the lineWidth is used. Defaults to 2.
     */
    public Integer medianWidth;
    
}

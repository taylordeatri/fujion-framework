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

import java.util.ArrayList;
import java.util.List;

import org.fujion.ancillary.Options;
import org.fujion.annotation.JavaScript;

/**
 * Options for buttons (e.g., print or export buttons).
 */
public class ButtonOptions extends Options {
    
    /**
     * Alignment for the buttons. Defaults to "right".
     */
    public AlignHorizontal align;
    
    /**
     * Background color for the buttons.
     */
    public String backgroundColor;
    
    /**
     * The border color of the buttons. Defaults to "#B0B0B0".
     */
    public String borderColor;
    
    /**
     * The border corner radius of the buttons. Defaults to 3.
     */
    public Integer borderRadius;
    
    /**
     * The border width of the buttons. Defaults to 1.
     */
    public Integer borderWidth;
    
    /**
     * Whether to enable buttons. Defaults to true.
     */
    public Boolean enabled;
    
    /**
     * Pixel height of the buttons. Defaults to 20.
     */
    public Integer height;
    
    /**
     * Color of the button border on hover. Defaults to #909090.
     */
    public String hoverBorderColor;
    
    /**
     * Defaults to #768F3E.
     */
    public String hoverSymbolFill;
    
    /**
     * Stroke (line) color for the symbol within the button on hover. Defaults to #4572A5.
     */
    public String hoverSymbolStroke;
    
    /**
     * A collection of config options for the menu items. Each options object consists of a text
     * option which is a string to show in the menu item, as well as an onclick parameter which is a
     * callback function to run on click. By default, there is one menu item for each of the
     * available export types. Menu items can be customized by defining a new array of items and
     * assigning null to unwanted positions.
     */
    public final List<ActionOptions> menuItems = new ArrayList<>();
    
    /**
     * A click handler callback to use on the button directly instead of the default. The "this"
     * variable will be the Highcharts object.
     */
    @JavaScript
    public String onclick;
    
    /**
     * The symbol for the button. Points to a definition function in the Highcharts.Renderer.symbols
     * collection. The default exportIcon function is part of the exporting module. Defaults to
     * "exportIcon".
     */
    public String symbol;
    
    /**
     * Defaults to #A8BF77.
     */
    public String symbolFill;
    
    /**
     * The pixel size of the symbol on the button. Defaults to 12.
     */
    public Integer symbolSize;
    
    /**
     * The color of the symbol's stroke or line. Defaults to "#A0A0A0".
     */
    public String symbolStroke;
    
    /**
     * The pixel stroke width of the symbol on the button. Defaults to 1.
     */
    public Integer symbolStrokeWidth;
    
    /**
     * The x position of the center of the symbol inside the button. Defaults to 11.5.
     */
    public Double symbolX;
    
    /**
     * The y position of the center of the symbol inside the button. Defaults to 10.5.
     */
    public Double symbolY;
    
    /**
     * A text string to add to the individual button. Defaults to null.
     */
    public String text;
    
    /**
     * The vertical alignment of the buttons. Can be one of "top", "middle" or "bottom". Defaults to
     * "top".
     */
    public AlignVertical verticalAlign;
    
    /**
     * The pixel width of the button. Defaults to 24.
     */
    public Integer width;
    
    /**
     * The horizontal position of the button relative to the align option. Defaults to 10.
     */
    public Integer x;
    
    /**
     * The vertical offset of the button's position relative to its verticalAlign. Defaults to 10.
     */
    public Integer y;
    
}

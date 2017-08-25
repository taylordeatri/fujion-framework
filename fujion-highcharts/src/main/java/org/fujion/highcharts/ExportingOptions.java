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

/**
 * Options for the exporting module.
 */
public class ExportingOptions extends Options {
    
    /**
     * Options for the export button.
     */
    public final ButtonOptions buttons_exportButton = new ButtonOptions();
    
    /**
     * Options for the print button.
     */
    public final ButtonOptions buttons_printButton = new ButtonOptions();
    
    /**
     * Whether to enable images in the export. Including image point markers, background images etc.
     * Defaults to false.
     */
    public Boolean enableImages;
    
    /**
     * Whether to enable the exporting module. Defaults to true.
     */
    public Boolean enabled;
    
    /**
     * The filename, without extension, to use for the exported chart. Defaults to "chart".
     */
    public String filename;
    
    /**
     * Default MIME type for exporting if chart.exportChart() is called without specifying a type
     * option. Possible values are image/png, image/jpeg, application/pdf and image/svg+xml.
     * Defaults to "image/png".
     */
    public ExportType type;
    
    /**
     * The URL for the server module converting the SVG string to an image format. By default this
     * points to Highslide Software's free web service. Defaults to http://export.highcharts.com.
     */
    public String url;
    
    /**
     * The pixel width of charts exported to PNG or JPG. Defaults to 800.
     */
    public Integer width;
    
}

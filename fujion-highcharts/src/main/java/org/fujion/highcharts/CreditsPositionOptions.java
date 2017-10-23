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
 * Position configuration for the credits label.
 */
public class CreditsPositionOptions extends Options {
    
    /**
     * The horizontal alignment. Defaults to "right".
     */
    public AlignHorizontal align;
    
    /**
     * The vertical alignment. Defaults to "bottom".
     */
    public AlignVertical verticalAlign;
    
    /**
     * The x offset. Defaults to -10.
     */
    public Integer x;
    
    /**
     * The y offset. Defaults to -5.
     */
    public Integer y;
}

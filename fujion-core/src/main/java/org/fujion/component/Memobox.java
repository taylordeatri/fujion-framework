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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.springframework.util.Assert;

/**
 * Component for entering multiple lines of text.
 */
@Component(tag = "memobox", widgetClass = "Memobox", parentTag = "*")
public class Memobox extends BaseInputboxComponent<String> {
    
    /**
     * Wrap mode for memo box.
     */
    public enum WrapMode {
        /**
         * Text is wrapped (contains newlines) when submitted in a form. When HARD is specified, the
         * cols property must also be specified.
         */
        HARD,
        /**
         * Text is not wrapped when submitted in a form.
         */
        SOFT
    }
    
    private boolean autoScroll;
    
    private WrapMode wrap = WrapMode.SOFT;
    
    private int rows = 2;

    private int cols = 20;
    
    public Memobox() {
        super();
        addStyle("resize", "none");
    }
    
    @Override
    @PropertyGetter("synchronized")
    public boolean getSynchronized() {
        return super.getSynchronized();
    }
    
    @Override
    @PropertySetter("synchronized")
    public void setSynchronized(boolean synchronize) {
        super.setSynchronized(synchronize);
    }
    
    /**
     * Returns the auto-scroll setting. If true, the control will ensure that the last line of input
     * is always visible, scrolling if necessary.
     *
     * @return The auto-scroll setting.
     */
    @PropertyGetter("autoScroll")
    public boolean isAutoScroll() {
        return autoScroll;
    }
    
    /**
     * Sets the auto-scroll setting. If true, the control will ensure that the last line of input is
     * always visible, scrolling if necessary.
     *
     * @param autoScroll The auto-scroll setting.
     */
    @PropertySetter("autoScroll")
    public void setAutoScroll(boolean autoScroll) {
        if (autoScroll != this.autoScroll) {
            propertyChange("autoScroll", this.autoScroll, this.autoScroll = autoScroll, true);
        }
    }
    
    /**
     * Returns the wrap mode.
     *
     * @return The wrap mode.
     * @see WrapMode
     */
    @PropertyGetter("wrap")
    public WrapMode getWrap() {
        return wrap;
    }

    /**
     * Sets the wrap mode.
     *
     * @param wrap The wrap mode.
     * @see WrapMode
     */
    @PropertySetter("wrap")
    public void setWrap(WrapMode wrap) {
        wrap = defaultify(wrap, WrapMode.SOFT);
        
        if (wrap != this.wrap) {
            propertyChange("wrap", this.wrap, this.wrap = wrap, true);
        }
    }
    
    /**
     * Returns the visible width of the input area in characters. The default is 20 characters. Also
     * affects the line break position when the wrap mode is set to HARD.
     *
     * @return The visible width of the input area in characters.
     */
    @PropertyGetter("cols")
    public int getCols() {
        return cols;
    }

    /**
     * Sets the visible width of the input area in characters. The default is 20 characters. Also
     * affects the line break position when the wrap mode is set to HARD.
     *
     * @param cols The visible width of the input area in characters.
     */
    @PropertySetter("cols")
    public void setCols(int cols) {
        if (cols != this.cols) {
            Assert.isTrue(cols > 0, "Cols must be greater than zero");
            propertyChange("cols", this.cols, this.cols = cols, true);
        }
    }
    
    /**
     * Returns the visible number of rows in the input area. The default is 2 rows.
     *
     * @return The visible number of rows in the input area.
     */
    @PropertyGetter("rows")
    public int getRows() {
        return rows;
    }

    /**
     * Sets the visible number of rows in the input area. The default is 2 rows.
     *
     * @param rows The visible number of rows in the input area.
     */
    @PropertySetter("rows")
    public void setRows(int rows) {
        if (rows != this.rows) {
            Assert.isTrue(rows > 0, "Rows must be greater than zero");
            propertyChange("rows", this.rows, this.rows = rows, true);
        }
    }
    
    @Override
    protected String _toValue(String value) {
        return value;
    }
    
    @Override
    protected String _toString(String value) {
        return value;
    }

}

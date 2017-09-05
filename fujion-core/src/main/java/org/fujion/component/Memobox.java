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
        HARD, SOFT
    }

    private boolean autoScroll;

    private WrapMode wrap = WrapMode.SOFT;

    private int rows = 2;

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

    @PropertyGetter("autoScroll")
    public boolean isAutoScroll() {
        return autoScroll;
    }

    @PropertySetter("autoScroll")
    public void setAutoScroll(boolean autoScroll) {
        if (autoScroll != this.autoScroll) {
            sync("autoScroll", this.autoScroll = autoScroll);
        }
    }

    @PropertyGetter("wrap")
    public WrapMode getWrap() {
        return wrap;
    }
    
    @PropertySetter("wrap")
    public void setWrap(WrapMode wrap) {
        wrap = defaultify(wrap, WrapMode.SOFT);

        if (wrap != this.wrap) {
            sync("wrap", this.wrap = wrap);
        }
    }
    
    @PropertyGetter("rows")
    public int getRows() {
        return rows;
    }
    
    @PropertySetter("rows")
    public void setRows(int rows) {
        if (rows != this.rows) {
            Assert.isTrue(rows > 0, "Rows must be greater than zero");
            sync("rows", this.rows = rows);
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

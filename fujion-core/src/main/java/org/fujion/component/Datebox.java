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

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.DateUtil;

/**
 * An input box for entering dates.
 */
@Component(tag = "datebox", widgetClass = "Datebox", parentTag = "*")
public class Datebox extends BaseInputboxComponent<Date> {

    private static final FastDateFormat serializer = FastDateFormat.getInstance("yyyy-MM-dd");

    private FastDateFormat formatter = FastDateFormat.getDateInstance(FastDateFormat.SHORT);

    private String format;

    @Override
    protected Date _toValue(String value) {
        return DateUtil.parseDate(value);
    }

    @Override
    protected String _toString(Date value) {
        return formatter.format(value);
    }

    @Override
    protected String _toClient(Date value) {
        return serializer.format(value);
    }

    /**
     * Returns the format for displaying the date.
     *
     * @return Format for displaying the date.
     * @see java.text.SimpleDateFormat
     */
    @PropertyGetter("format")
    public String getFormat() {
        return format;
    }

    /**
     * Sets the format for displaying the date.
     *
     * @param format Format for displaying the date.
     * @see java.text.SimpleDateFormat
     */
    @PropertySetter("format")
    public void setFormat(String format) {
        if (!areEqual(format = trimify(format), this.format)) {
            this.format = format;
            formatter = format == null ? FastDateFormat.getDateInstance(FastDateFormat.SHORT)
                    : FastDateFormat.getInstance(format);
        }
    }

}

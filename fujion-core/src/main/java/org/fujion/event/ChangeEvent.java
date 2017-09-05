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
package org.fujion.event;

import org.fujion.ancillary.ConvertUtil;
import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

/*
 * A change event.
 */
@EventType(ChangeEvent.TYPE)
public class ChangeEvent extends Event {
    
    public static final String TYPE = "change";
    
    @EventParameter
    private Object value;
    
    public ChangeEvent() {
        super(TYPE);
    }
    
    public ChangeEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }
    
    public ChangeEvent(BaseComponent target, Object data, Object value) {
        super(TYPE, target, data);
        this.value = value;
    }
    
    public Object getValue() {
        return value;
    }
    
    public <T> T getValue(Class<T> type) {
        return ConvertUtil.convert(value, type);
    }
}

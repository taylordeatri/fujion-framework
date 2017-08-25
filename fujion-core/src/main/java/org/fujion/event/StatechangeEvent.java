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
package org.fujion.event;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;

@EventType(StatechangeEvent.TYPE)
public class StatechangeEvent extends Event {
    
    public static final String TYPE = "statechange";
    
    @EventParameter
    private String state;
    
    @EventParameter(onFailure = OnFailure.IGNORE)
    private Object value;
    
    public StatechangeEvent() {
        super(TYPE);
    }
    
    public String getState() {
        return state;
    }
    
    public Object getValue() {
        return value;
    }
}

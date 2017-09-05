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

import java.io.InputStream;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

/**
 * An upload event.
 */
@EventType(UploadEvent.TYPE)
public class UploadEvent extends MouseEvent {
    
    /**
     * Upload stage reported by this event.
     */
    public enum UploadState {
        UNKNOWN, MAXSIZE, ABORTED, EMPTY, LOADING, DONE
    }
    
    @EventParameter
    private String file;
    
    @EventParameter
    private InputStream blob;
    
    @EventParameter
    private int total;
    
    @EventParameter
    private int loaded;
    
    @EventParameter
    private int state;
    
    public static final String TYPE = "upload";
    
    public UploadEvent() {
        super(TYPE);
    }
    
    public UploadEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }
    
    public String getFile() {
        return file;
    }
    
    public InputStream getBlob() {
        return blob;
    }
    
    public int getTotal() {
        return total;
    }
    
    public int getLoaded() {
        return loaded;
    }
    
    public UploadState getState() {
        try {
            return UploadState.values()[state + 3];
        } catch (Exception e) {
            return UploadState.UNKNOWN;
        }
    }
}

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
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.CloseEvent;
import org.fujion.event.OpenEvent;

/**
 * A detail component.
 */
@Component(tag = "detail", widgetModule = "fujion-detail", widgetClass = "Detail", content = ContentHandling.AS_CHILD, parentTag = "*", childTag = @ChildTag("*"))
public class Detail extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionNone> {

    private boolean open;

    public Detail() {
        this(null);
    }

    public Detail(String label) {
        super(label);
    }
    
    /**
     * Returns true if the detail view is open.
     *
     * @return True if the detail view is open.
     */
    @PropertyGetter("open")
    public boolean isOpen() {
        return open;
    }
    
    /**
     * Set the detail view open state.
     *
     * @param open The detail view open state.
     */
    @PropertySetter("open")
    public void setOpen(boolean open) {
        propertyChange("open", this.open, this.open = open, true);
    }

    /**
     * Handles an open event from the client.
     *
     * @param event An open event.
     */
    @EventHandler(value = "open", syncToClient = false)
    private void _onOpen(OpenEvent event) {
        open = true;
    }
    
    /**
     * Handles an close event from the client.
     *
     * @param event A close event.
     */
    @EventHandler(value = "close", syncToClient = false)
    private void _onClose(CloseEvent event) {
        open = false;
    }
}

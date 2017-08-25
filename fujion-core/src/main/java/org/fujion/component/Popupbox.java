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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.CloseEvent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;
import org.fujion.event.OpenEvent;

/**
 * A popup box component is a text box with a drop down button that triggers the appearance of a
 * popup component. A popup component may be specified as a property or inline as a child.
 */
@Component(tag = "popupbox", widgetClass = "Popupbox", parentTag = "*", childTag = @ChildTag(value = "popup", maximum = 1))
public class Popupbox extends Textbox {

    private boolean open;

    public void open() {
        setOpen(true);
    }

    public void close() {
        setOpen(false);
    }

    @Override
    protected void afterAddChild(BaseComponent child) {
        super.afterAddChild(child);

        if (getPage() != null) {
            setPopup((Popup) child);
        }
    }

    @Override
    protected void onAttach(Page page) {
        super.onAttach(page);

        if (getChildCount() > 0) {
            setPopup((Popup) getFirstChild());
        }
    }

    @Override
    public void setPopup(Popup popup) {
        BaseComponent child = this.getFirstChild();

        if (child != null && child != popup) {
            throw new IllegalArgumentException("You may not set a popup reference when a child popup is present.");
        }

        if (popup != getPopup()) {
            open = false;
        }

        super.setPopup(popup);
    }

    @PropertyGetter("open")
    public boolean isOpen() {
        return open;
    }

    @PropertySetter("open")
    public void setOpen(boolean open) {
        if (open != this.open) {
            invoke((this.open = open) ? "open" : "close");
        }
    }

    @EventHandler(value = { "popupopen", "popupclose" })
    private void _onOpen(Event event) {
        boolean open = "popupopen".equals(event.getType());
        
        if (open != this.open) {
            this.open = open;
            event = open ? new OpenEvent(this, event.getData()) : new CloseEvent(this, event.getData());
            EventUtil.send(event);
        }
    }

}

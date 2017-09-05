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

import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

/**
 * Base class for mouse-related events.
 */
public abstract class MouseEvent extends Event {

    /**
     * Mouse button specifier.
     */
    public enum MouseButton {
        UNSPECIFIED, LEFT, MIDDLE, RIGHT
    }

    @EventParameter
    private int pageX;

    @EventParameter
    private int pageY;

    @EventParameter
    private int which;

    protected MouseEvent(String type) {
        super(type);
    }

    protected MouseEvent(String type, BaseComponent target, Object data) {
        super(type, target, data);
    }

    public int getPageX() {
        return pageX;
    }

    public int getPageY() {
        return pageY;
    }

    public MouseButton getMouseButton() {
        return MouseButton.values()[which];
    }

}

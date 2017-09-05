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
 * Base class for key-related events.
 */
public abstract class KeyEvent extends Event {

    @EventParameter
    private int keyCode;

    @EventParameter
    private char charCode;

    @EventParameter
    private boolean shiftKey;

    @EventParameter
    private boolean ctrlKey;

    @EventParameter
    private boolean altKey;

    @EventParameter
    private boolean metaKey;

    protected KeyEvent(String type) {
        super(type);
    }

    protected KeyEvent(String type, BaseComponent target, Object data) {
        super(type, target, data);
    }

    public boolean isAltKey() {
        return altKey;
    }

    public boolean isCtrlKey() {
        return ctrlKey;
    }

    public boolean isShiftKey() {
        return shiftKey;
    }

    public boolean isMetaKey() {
        return metaKey;
    }

    public KeyCode getKeyCode() {
        return KeyCode.fromCode(keyCode);
    }

    public char getCharCode() {
        return charCode;
    }

    /**
     * Returns the key capture representation of the typed key.
     *
     * @return The key capture representation of the typed key.
     */
    public String getKeycapture() {
        StringBuilder sb = new StringBuilder();

        if (isCtrlKey()) {
            sb.append('^');
        }

        if (isAltKey()) {
            sb.append('@');
        }

        if (isMetaKey()) {
            sb.append('~');
        }

        if (isShiftKey()) {
            sb.append('$');
        }

        sb.append("#").append(keyCode);
        return sb.toString();
    }

}

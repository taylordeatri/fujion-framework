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

/**
 * Wrapper for a forwarded event.
 */
public class ForwardedEvent extends Event {

    private final Event originalEvent;

    public ForwardedEvent(String forwardType, Event originalEvent) {
        super(forwardType, originalEvent);
        this.originalEvent = originalEvent;
    }

    /**
     * Returns the original event being forwarded.
     *
     * @return The original event being forwarded.
     */
    public Event getOriginalEvent() {
        return originalEvent;
    }
}

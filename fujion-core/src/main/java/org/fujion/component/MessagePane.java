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
import org.fujion.annotation.EventHandler;

/**
 * A pane holding a single message in a message window.
 */
@Component(tag = "messagepane", widgetClass = "Messagepane", content = ContentHandling.AS_CHILD, parentTag = "messagewindow", childTag = @ChildTag("*"))
public class MessagePane extends BaseUIComponent {

    private String title;

    private int duration = 8000;

    private String category;

    private boolean actionable;

    public MessagePane() {

    }

    public MessagePane(String title, String category, int duration, boolean actionable) {
        setTitle(title);
        setCategory(category);
        setDuration(duration);
        setActionable(actionable);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!areEqual(title = nullify(title), this.title)) {
            sync("title", this.title = title);
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration != this.duration) {
            sync("duration", this.duration = duration);
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = nullify(category);
    }

    public boolean isActionable() {
        return actionable;
    }

    public void setActionable(boolean actionable) {
        if (actionable != this.actionable) {
            sync("actionable", this.actionable = actionable);
        }
    }

    @EventHandler(value = "close", syncToClient = false)
    private void _close() {
        destroy();
    }
}

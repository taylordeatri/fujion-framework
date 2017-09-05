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

/**
 * A single pane within a pane view.
 */
@Component(tag = "pane", widgetModule = "fujion-paneview", widgetClass = "Pane", content = ContentHandling.AS_CHILD, parentTag = "paneview", childTag = @ChildTag("*"))
public class Pane extends BaseUIComponent {

    private boolean splittable;

    private String title;

    @PropertyGetter("splittable")
    public boolean isSplittable() {
        return splittable;
    }

    @PropertySetter("splittable")
    public void setSplittable(boolean splittable) {
        if (splittable != this.splittable) {
            sync("splittable", this.splittable = splittable);
        }
    }

    @PropertyGetter("title")
    public String getTitle() {
        return title;
    }

    @PropertySetter("title")
    public void setTitle(String title) {
        if (!areEqual(title = nullify(title), this.title)) {
            sync("title", this.title = title);
        }
    }

}

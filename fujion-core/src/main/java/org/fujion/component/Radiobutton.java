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
import org.fujion.event.ChangeEvent;
import org.fujion.event.EventUtil;

/**
 * A component representing a single radio button.
 */
@Component(tag = "radiobutton", widgetClass = "Radiobutton", parentTag = "*")
public class Radiobutton extends Checkbox {

    public Radiogroup getGroup() {
        return getAncestor(Radiogroup.class);
    }

    @Override
    protected void _onChange(ChangeEvent event) {
        super._onChange(event);

        if (this.isChecked()) {
            Radiogroup group = getGroup();

            if (group != null) {
                event = new ChangeEvent(group, event.getData(), this);
                EventUtil.send(event);
            }
        }
    }
}

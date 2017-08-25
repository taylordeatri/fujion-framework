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
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A toolbar component.
 */
@Component(tag = "toolbar", widgetClass = "Toolbar", content = ContentHandling.AS_CHILD, parentTag = "*", childTag = @ChildTag("*"))
public class Toolbar extends BaseUIComponent {
    
    public enum Alignment {
        START, CENTER, END
    }
    
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
    
    private Alignment alignment = Alignment.START;
    
    private Orientation orientation = Orientation.HORIZONTAL;
    
    @PropertyGetter("alignment")
    public Alignment getAlignment() {
        return alignment;
    }
    
    @PropertySetter("alignment")
    public void setAlignment(Alignment alignment) {
        alignment = alignment == null ? Alignment.START : alignment;
        
        if (alignment != this.alignment) {
            sync("alignment", this.alignment = alignment);
        }
    }
    
    @PropertyGetter("orientation")
    public Orientation getOrientation() {
        return orientation;
    }
    
    @PropertySetter("orientation")
    public void setOrientation(Orientation orientation) {
        orientation = orientation == null ? Orientation.HORIZONTAL : orientation;
        
        if (orientation != this.orientation) {
            sync("orientation", this.orientation = orientation);
        }
    }
}

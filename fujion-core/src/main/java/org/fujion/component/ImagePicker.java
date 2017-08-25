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

/**
 * A component for selecting an image from a collection of images.
 */
@Component(tag = "imagepicker", widgetModule = "fujion-picker", widgetClass = "Imagepicker", parentTag = "*", childTag = @ChildTag("imagepickeritem"))
public class ImagePicker extends BasePickerComponent<String> {
    
    @Component(tag = "imagepickeritem", widgetModule = "fujion-picker", widgetClass = "Imagepickeritem", parentTag = "imagepicker")
    public static class ImagePickeritem extends BasePickerItem<String> {
        
        public ImagePickeritem() {
            
        }
        
        public ImagePickeritem(String item) {
            super(item);
        }
        
        @Override
        protected String _toString(String value) {
            return value;
        }
        
        @Override
        protected String _toValue(String text) {
            return text;
        }
        
    }
    
    public ImagePicker() {
        super(ImagePickeritem.class);
    }
    
}

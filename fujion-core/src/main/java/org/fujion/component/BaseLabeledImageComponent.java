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

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * The base class for components that have an associated label and image.
 *
 * @param <P> The type of label positioning that is supported.
 */
public abstract class BaseLabeledImageComponent<P extends BaseLabeledComponent.ILabelPosition> extends BaseLabeledComponent<P> {

    private String image;

    public BaseLabeledImageComponent() {
    }

    public BaseLabeledImageComponent(String label) {
        setLabel(label);
    }

    /**
     * Returns the URL of the image associated with this component.
     *
     * @return URL of the associated image (or null if none).
     */
    @PropertyGetter("image")
    public String getImage() {
        return image;
    }

    /**
     * Sets the URL of the image associated with this component.
     *
     * @param image URL of the associated image, or null to indicate no image.
     */
    @PropertySetter("image")
    public void setImage(String image) {
        propertyChange("image", this.image, this.image = nullify(image), true);
    }

}

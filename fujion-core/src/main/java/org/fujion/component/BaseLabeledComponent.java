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

import org.fujion.ancillary.ILabeled;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * The base class for components that have an associated label.
 *
 * @param <P> The type of label positioning that is supported.
 */
public abstract class BaseLabeledComponent<P extends BaseLabeledComponent.ILabelPosition> extends BaseUIComponent implements ILabeled {

    public interface ILabelPosition {};

    public enum LabelPositionHorz implements ILabelPosition {
        RIGHT, LEFT
    }

    public enum LabelPositionAll implements ILabelPosition {
        RIGHT, LEFT, TOP, BOTTOM
    }

    public enum LabelPositionNone implements ILabelPosition {}

    private String label;

    private P position;

    public BaseLabeledComponent() {
    }

    public BaseLabeledComponent(String label) {
        setLabel(label);
    }

    @Override
    @PropertyGetter("label")
    public String getLabel() {
        return label;
    }

    @Override
    @PropertySetter("label")
    public void setLabel(String label) {
        if (!areEqual(label = nullify(label), this.label)) {
            sync("label", this.label = label);
        }
    }

    protected P getPosition() {
        return position;
    }

    protected void setPosition(P position) {
        if (position != this.position) {
            sync("position", this.position = position);
        }
    }

}

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
package org.fujion.testharness;

import org.fujion.annotation.EventHandler;
import org.fujion.annotation.OnFailure;
import org.fujion.annotation.WiredComponent;
import org.fujion.client.ClientUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.BaseLabeledComponent.LabelPositionAll;
import org.fujion.component.Button;
import org.fujion.component.Caption;
import org.fujion.component.Caption.LabelAlignment;
import org.fujion.component.Checkbox;
import org.fujion.component.Detail;
import org.fujion.component.Div;
import org.fujion.component.Popup;
import org.fujion.component.Radiogroup;
import org.fujion.event.ChangeEvent;
import org.fujion.event.Event;
import org.fujion.page.PageUtil;

/**
 * Demonstration of miscellaneous capabilities.
 */
public class MiscController extends BaseController {
    
    @WiredComponent(onFailure = OnFailure.IGNORE)
    private Div nomatch;
    
    @WiredComponent
    private BaseComponent dynamicContent;
    
    @WiredComponent
    private Popup contextMenu;
    
    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        log(nomatch == null, "Component 'nomatch' was correctly not wired.", "Component 'nomatch' as erroneously wired.");
        PageUtil.createPageFromContent("<button label='Dynamic Content' class='flavor:btn-danger'/>", dynamicContent);
    }
    
    /**
     * Controls whether or not application closure is challenged.
     *
     * @param event The checkbox change event.
     */
    @EventHandler(value = "change", target = "chkPreventClosure")
    public void chkPreventClosureHandler(ChangeEvent event) {
        ClientUtil.canClose(!((Checkbox) event.getTarget()).isChecked());
    }
    
    @EventHandler(value = "click", target = "btnSaveAsFile")
    public void btnSaveAsFileHandler() {
        ClientUtil.saveToFile("This is test content", "text/plain", "testFile.txt");
    }
    
    @WiredComponent
    private Div divMaskTest;
    
    private boolean masked;
    
    @EventHandler(value = "click", target = "btnMaskTest")
    private void btnMaskTestClickHandler() {
        if (masked = !masked) {
            divMaskTest.addMask("Mask Test", contextMenu);
        } else {
            divMaskTest.removeMask();
        }
    }
    
    @WiredComponent
    private Button btnToggleBalloon;
    
    @EventHandler(value = "click", target = "@btnToggleBalloon")
    private void btnToggleBalloonClickHandler() {
        if (btnToggleBalloon.getBalloon() == null) {
            btnToggleBalloon.setBalloon("Balloon Text");
        } else {
            btnToggleBalloon.setBalloon(null);
        }
    }
    
    @WiredComponent
    private Caption caption;

    @WiredComponent
    private Radiogroup rgPosition;

    @EventHandler(value = "change", target = "@rgPosition")
    private void positionChangeHandler() {
        String value = rgPosition.getSelected().getLabel();
        LabelPositionAll position = LabelPositionAll.valueOf(value.toUpperCase());
        caption.setPosition(position);
    }

    @WiredComponent
    private Radiogroup rgAlignment;
    
    @EventHandler(value = "change", target = "@rgAlignment")
    private void alignmentChangeHandler() {
        String value = rgAlignment.getSelected().getLabel();
        LabelAlignment alignment = LabelAlignment.valueOf(value.toUpperCase());
        caption.setAlignment(alignment);
    }
    
    @WiredComponent
    private Detail detail;

    @EventHandler(value = "click", target = "btnToggleDetail")
    private void toggleDetailHandler(Event event) {
        detail.setOpen(!detail.isOpen());
    }

}

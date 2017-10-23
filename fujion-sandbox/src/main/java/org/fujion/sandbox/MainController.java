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
package org.fujion.sandbox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.fujion.ancillary.IAutoWired;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.codemirror.CodeMirror;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.Combobox;
import org.fujion.component.Comboitem;
import org.fujion.component.Label;
import org.fujion.component.Namespace;
import org.fujion.component.Window;
import org.fujion.component.Window.Mode;
import org.fujion.event.EventUtil;
import org.fujion.model.IComponentRenderer;
import org.fujion.model.ListModel;
import org.fujion.page.PageUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * Plugin to facilitate testing of Fujion layouts.
 */
public class MainController implements IAutoWired, ApplicationContextAware {

    private static final Mode[] REPLACE_MODES = { Mode.MODAL, Mode.POPUP };

    private static final Comparator<Resource> resourceComparator = (r1, r2) -> {
        return r1.getFilename().compareToIgnoreCase(r2.getFilename());
    };

    private static final IComponentRenderer<Comboitem, Resource> fujionRenderer = new IComponentRenderer<Comboitem, Resource>() {

        @Override
        public Comboitem render(Resource resource) {
            Comboitem item = new Comboitem();
            item.setData(resource);
            item.setLabel(resource.getFilename());
            item.setHint(getPath(resource));
            return item;
        }

        private String getPath(Resource resource) {
            try {
                String[] pcs = resource.getURL().toString().split("!", 2);

                if (pcs.length == 1) {
                    return pcs[0];
                }

                int i = pcs[0].lastIndexOf('/') + 1;
                return pcs[0].substring(i) + ":\n\n" + pcs[1];
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

    };

    // Start of auto-wired section

    @WiredComponent
    private CodeMirror editor;

    @WiredComponent
    private Combobox cboFujion;

    @WiredComponent
    private BaseComponent contentParent;

    // End of auto-wired section

    private Namespace contentBase;

    private BaseComponent root;

    private String content;

    private final ListModel<Resource> model = new ListModel<>();

    /**
     * Find the content base component. We can't assign it an id because of potential id collisions.
     */
    @Override
    public void afterInitialized(BaseComponent comp) {
        this.root = comp;
        cboFujion.setRenderer(fujionRenderer);
        cboFujion.setModel(model);
        cboFujion.setVisible(model.size() > 0);
        contentBase = contentParent.getChild(Namespace.class);
    }

    /**
     * Refreshes the view based on the current contents.
     */
    @EventHandler("refresh")
    public void refresh() {
        contentBase.destroyChildren();

        if (content != null && !content.isEmpty()) {
            try {
                EventUtil.post("modeCheck", this.root, null);
                PageUtil.createPageFromContent(content, contentBase);
            } catch (Exception e) {
                contentBase.destroyChildren();
                Label label = new Label(ExceptionUtils.getStackTrace(e));
                contentBase.addChild(label);
            }
        }
    }

    /**
     * Sets the focus to the editor window.
     */
    @EventHandler("activate")
    public void focus() {
        editor.focus();
    }

    /**
     * Check for unsupported window modes. This is done asynchronously to allow modal windows to
     * also be checked.
     */
    @EventHandler(value = "modeCheck", target = "^")
    private void onModeCheck() {
        modeCheck(contentBase);
    }

    /**
     * Check for any window components with mode settings that need to be changed.
     *
     * @param comp Current component in search.
     */
    private void modeCheck(BaseComponent comp) {
        if (comp instanceof Window) {
            Window win = (Window) comp;

            if (win.isVisible() && ArrayUtils.contains(REPLACE_MODES, win.getMode())) {
                win.setMode(Mode.INLINE);
            }
        }

        for (BaseComponent child : comp.getChildren()) {
            modeCheck(child);
        }
    }

    /**
     * Renders the updated fujion content in the view pane.
     */
    @EventHandler(value = "click", target = "btnRenderContent")
    private void onClick$btnRenderContent() {
        content = editor.getValue();
        refresh();
        focus();
    }

    /**
     * Clears combo box selection when content is cleared.
     */
    @EventHandler(value = "click", target = "btnClearContent")
    private void onClick$btnClearContent() {
        editor.clear();
        cboFujion.setSelectedItem(null);
        cboFujion.setHint(null);
    }

    /**
     * Clears the view pane.
     */
    @EventHandler(value = "click", target = "btnClearView")
    private void onClick$btnClearView() {
        contentBase.destroyChildren();
        focus();
    }

    /**
     * Re-renders content in the view pane.
     */
    @EventHandler(value = "click", target = "btnRefreshView")
    private void onClick$btnRefreshView() {
        refresh();
    }

    @EventHandler(value = "click", target = "btnFormatContent")
    private void onClick$btnFormatContent() {
        editor.format();
    }

    /**
     * Load contents of newly selected fujion document.
     *
     * @throws IOException Exception on reading fujion document.
     */
    @EventHandler(value = "change", target = "@cboFujion")
    private void onChange$cboFujion() throws IOException {
        Comboitem item = cboFujion.getSelectedItem();
        cboFujion.setHint(null);
        Resource resource = item == null ? null : item.getData(Resource.class);

        if (resource != null) {
            try (InputStream is = resource.getInputStream()) {
                content = IOUtils.toString(is, StandardCharsets.UTF_8);
                cboFujion.setHint(item.getHint());
                editor.setValue(content);
                focus();
            }
        }
    }

    /**
     * Populate combo box model with all fujion documents on class path.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        findResources(applicationContext, "classpath*:**/*.fsp");
        findResources(applicationContext, "**/*.fsp");
        model.sort(resourceComparator, true);
    }

    private void findResources(ApplicationContext applicationContext, String pattern) {
        try {
            for (Resource resource : applicationContext.getResources(pattern)) {
                model.add(resource);
            }
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

}

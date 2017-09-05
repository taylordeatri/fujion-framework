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

import java.util.Iterator;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.Treenode.TreenodeIterator;
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

/**
 * A component acting as a root for a hierarchical collection of nodes.
 */
@Component(tag = "treeview", widgetModule = "fujion-treeview", widgetClass = "Treeview", parentTag = "*", childTag = @ChildTag("treenode"))
public class Treeview extends BaseUIComponent implements Iterable<Treenode>, ISupportsModel<Treenode> {
    
    private boolean showRoot;
    
    private boolean showLines = true;
    
    private boolean showToggles = true;
    
    private Treenode selectedNode;
    
    private final ModelAndView<Treenode, Object> modelAndView = new ModelAndView<>(this);
    
    @PropertyGetter("showRoot")
    public boolean getShowRoot() {
        return showRoot;
    }
    
    @PropertySetter("showRoot")
    public void setShowRoot(boolean showRoot) {
        if (showRoot != this.showRoot) {
            sync("showRoot", this.showRoot = showRoot);
        }
    }
    
    @PropertyGetter("showLines")
    public boolean getShowLines() {
        return showLines;
    }
    
    @PropertySetter("showLines")
    public void setShowLines(boolean showLines) {
        if (showLines != this.showLines) {
            sync("showLines", this.showLines = showLines);
        }
    }
    
    @PropertyGetter("showToggles")
    public boolean getShowToggles() {
        return showToggles;
    }
    
    @PropertySetter("showToggles")
    public void setShowToggles(boolean showToggles) {
        if (showToggles != this.showToggles) {
            sync("showToggles", this.showToggles = showToggles);
        }
    }
    
    public void collapseAll() {
        expandOrCollapse(this, true);
    }
    
    public void expandAll() {
        expandOrCollapse(this, false);
    }
    
    private void expandOrCollapse(BaseComponent parent, boolean collapse) {
        for (BaseComponent child : parent.getChildren()) {
            ((Treenode) child).setCollapsed(collapse);
            expandOrCollapse(child, collapse);
        }
    }
    
    public Treenode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(Treenode selectedNode) {
        if (selectedNode == this.selectedNode) {
            return;
        }
        
        if (this.selectedNode != null) {
            this.selectedNode._setSelected(false, true, false);
        }
        
        this.selectedNode = selectedNode;
        
        if (selectedNode != null) {
            selectedNode._setSelected(true, true, false);
        }
    }
    
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (child.isAncestor(selectedNode)) {
            selectedNode = null;
        }
    }
    
    @Override
    protected void afterAddChild(BaseComponent child) {
        Treenode selnode = findSelected((Treenode) child, null);
        
        if (selnode != null) {
            setSelectedNode(selnode);
        }
    }
    
    private Treenode findSelected(Treenode node, Treenode selnode) {
        if (node.isSelected()) {
            if (selnode != null) {
                selnode._setSelected(false, true, false);
            }
            
            selnode = node;
        }
        
        for (BaseComponent child : node.getChildren()) {
            selnode = findSelected((Treenode) child, selnode);
        }
        
        return selnode;
    }
    
    @Override
    public Iterator<Treenode> iterator() {
        return new TreenodeIterator(this);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        modelAndView.destroy();
    }
    
    @Override
    public IModelAndView<Treenode, ?> getModelAndView() {
        return modelAndView;
    }
    
}

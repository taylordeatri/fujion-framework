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
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;

/**
 * A single node in a tree view.
 */
@Component(tag = "treenode", widgetModule = "fujion-treeview", widgetClass = "Treenode", parentTag = { "treeview",
        "treenode" }, childTag = @ChildTag("treenode"))
public class Treenode extends BaseLabeledImageComponent<BaseLabeledComponent.LabelPositionNone> implements Iterable<Treenode> {

    /**
     * Iterates over nodes in a tree in a depth first search. Is not susceptible to concurrent
     * modification errors if tree composition changes during iteration.
     */
    protected static class TreenodeIterator implements Iterator<Treenode> {

        private Treenode current;

        private Treenode next;

        private int level;

        /**
         * Iterates all descendants of root node.
         *
         * @param root The root node.
         */
        public TreenodeIterator(BaseComponent root) {
            next = root == null ? null : root.getChild(Treenode.class);
        }

        /**
         * Advances to next tree node following specified node.
         *
         * @return Next tree node or null if no more.
         */
        private Treenode advance() {
            if (current == null || level < 0) {
                return null;
            }

            next = (Treenode) current.getFirstChild();

            if (next != null) {
                level++;
                return next;
            }

            next = (Treenode) current.getNextSibling();

            if (next != null) {
                return next;
            }

            next = current;

            while (--level >= 0) {
                BaseComponent parent = next.getParent();

                if (!(parent instanceof Treenode)) {
                    level = -1;
                } else {
                    next = (Treenode) parent.getNextSibling();

                    if (next == null) {
                        next = (Treenode) parent;
                    } else {
                        break;
                    }
                }
            }

            return level < 0 ? null : next;
        }

        /**
         * Returns next tree node.
         *
         * @return The next tree node.
         */
        private Treenode nextNode() {
            return next == null ? advance() : next;
        }

        /**
         * Returns true if iterator not at end.
         */
        @Override
        public boolean hasNext() {
            return nextNode() != null;
        }

        /**
         * Returns next tree item, preparing internal state to retrieve next node.
         */
        @Override
        public Treenode next() {
            current = nextNode();
            next = null;
            return current;
        }

    }

    private boolean collapsed;

    private boolean selected;

    private int badgeCounter;

    /**
     * Returns the selected state.
     *
     * @return The selected state.
     */
    @PropertyGetter("selected")
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected state.
     *
     * @param selected The selected state.
     */
    @PropertySetter("selected")
    public void setSelected(boolean selected) {
        _setSelected(selected, true, true);
    }

    /**
     * Set the selected state of this node.
     *
     * @param selected The new selected state.
     * @param notifyClient If true, notify the client of the state change.
     * @param notifyParent If true, notify the parent tree view of the state change.
     */
    /*package*/ void _setSelected(boolean selected, boolean notifyClient, boolean notifyParent) {
        if (propertyChange("selected", this.selected, this.selected = selected, notifyClient) && notifyParent) {
            Treeview treeview = getTreeview();

            if (treeview != null) {
                if (selected) {
                    treeview.setSelectedNode(this);
                } else if (treeview.getSelectedNode() == this) {
                    treeview.setSelectedNode(null);
                }
            }
        }
    }

    private void _setTreeSelected(Treenode selectedNode) {
        Treeview treeview = getTreeview();

        if (treeview != null) {
            treeview.setSelectedNode(selectedNode);
        }
    }

    /**
     * Returns the parent tree view, if any.
     *
     * @return The parent tree view (may be null).
     */
    public Treeview getTreeview() {
        return getAncestor(Treeview.class);
    }

    /**
     * If the added node is selected, update the parent tree view's selection state.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        if (((Treenode) child).isSelected()) {
            _setTreeSelected((Treenode) child);
        }
    }

    /**
     * If the removed node is selected, update the parent tree view's selection state.
     *
     * @see org.fujion.component.BaseComponent#beforeRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void beforeRemoveChild(BaseComponent child) {
        if (((Treenode) child).isSelected()) {
            _setTreeSelected(null);
        }
    }

    /**
     * Return true if the node is collapsed (i.e., its children are hidden).
     *
     * @return True if the node is collapsed.
     */
    @PropertyGetter("collapsed")
    public boolean isCollapsed() {
        return collapsed;
    }

    /**
     * Set to true to collapse the node, false to expand it.
     *
     * @param collapsed True to collapse the node, false to expand it
     */
    @PropertySetter("collapsed")
    public void setCollapsed(boolean collapsed) {
        propertyChange("collapsed", this.collapsed, this.collapsed = collapsed, true);
    }

    /**
     * Ensures that this node is visible (i.e., all of its parent tree nodes are expanded.
     */
    public void makeVisible() {
        BaseComponent node = getParent();

        while (node instanceof Treenode) {
            ((Treenode) node).setCollapsed(false);
            node = node.getParent();
        }

        scrollIntoView();
    }

    /**
     * Handles toggle events from the client.
     */
    @EventHandler(value = "toggle", syncToClient = false)
    private void _onToggle() {
        collapsed = !collapsed;
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false)
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), false), false, true);
        Treeview tree = getTreeview();

        if (tree != null) {
            event = new ChangeEvent(tree, event.getData(), this);
            EventUtil.send(event);
        }
    }

    /**
     * Handles badge update events.
     *
     * @param event A badge update event.
     */
    @EventHandler("badge")
    private void _onBadge(Event event) {
        int delta = (Integer) event.getData();

        if (delta != 0) {
            badgeCounter += delta;
            sync("badge", badgeCounter);
        }
    }

    /**
     * Returns an iterator of all children of this node.
     *
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Treenode> iterator() {
        return new TreenodeIterator(this);
    }
}

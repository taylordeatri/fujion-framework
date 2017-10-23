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

import java.util.Comparator;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.model.IListModel;
import org.fujion.model.SmartComparator;
import org.fujion.model.Sorting.SortOrder;
import org.fujion.model.Sorting.SortToggle;

/**
 * A component representing a single column within a grid.
 */
@Component(tag = "column", widgetClass = "Column", widgetModule = "fujion-grid", parentTag = "columns", childTag = @ChildTag("*"))
public class Column extends BaseLabeledImageComponent<BaseLabeledComponent.LabelPositionNone> {

    private Comparator<?> sortComparator;

    private SortOrder sortOrder = SortOrder.UNSORTED;

    private SortToggle sortToggle;

    private boolean sortColumn;

    public Column() {
        super();
    }

    public Column(String label) {
        super(label);
    }

    /**
     * Returns the comparator to be used for sorting (if any).
     *
     * @return The comparator to be used for sorting. May be null.
     */
    public Comparator<?> getSortComparator() {
        return sortComparator;
    }

    /**
     * Sets the comparator to be used for sorting.
     *
     * @param sortComparator The comparator to be used for sorting. May be null.
     */
    public void setSortComparator(Comparator<?> sortComparator) {
        if (sortComparator != this.sortComparator) {
            this.sortComparator = sortComparator;
            this.sortOrder = SortOrder.UNSORTED;
            updateClient();
        }
    }

    /**
     * Sets the name of the model property to be used for sorting.
     *
     * @param propertyName The name of the model property to be used for sorting.
     */
    @PropertySetter("sortBy")
    public void setSortComparator(String propertyName) {
        setSortComparator(new SmartComparator(propertyName));
    }

    /**
     * Returns the sort order. This may not reflect the current sort order. Rather, it specifies the
     * ordering to be used when the <code>sort</code> method is invoked.
     *
     * @return The sort order.
     */
    @PropertyGetter("sortOrder")
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sort order. This does not affect the current sort order. Rather, it specifies the
     * ordering to be used when the <code>sort</code> method is invoked.
     *
     * @param sortOrder The sort order.
     */
    @PropertySetter("sortOrder")
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder == null ? SortOrder.UNSORTED : sortOrder;
    }

    /**
     * Returns the type of sort toggle.
     *
     * @return The type of sort toggle.
     */
    @PropertyGetter("sortToggle")
    public SortToggle getSortToggle() {
        return sortToggle;
    }

    /**
     * Sets the type of sort toggle.
     *
     * @param sortToggle The type of sort toggle.
     */
    @PropertySetter("sortToggle")
    public void setSortToggle(SortToggle sortToggle) {
        this.sortToggle = sortToggle;
    }

    /**
     * Transitions the sort order to the next state (depending on the setting of the sort toggle)
     * and performs the sort.
     */
    public void toggleSort() {
        int i = sortOrder.ordinal() + 1;
        int max = sortToggle == SortToggle.TRISTATE ? 3 : 2;
        setSortOrder(SortOrder.values()[i >= max ? 0 : i]);
        sort();
    }

    /**
     * Sort the column according to the sort order property.
     */
    public void sort() {
        if (!sortColumn) {
            setSortColumn(true);
            return;
        }

        IListModel<Object> model = sortComparator == null || sortOrder == SortOrder.UNSORTED ? null : getModel();
        updateClient();

        if (model != null) {
            @SuppressWarnings("unchecked")
            Comparator<Object> comparator = sortOrder == SortOrder.NATIVE ? null : (Comparator<Object>) sortComparator;
            model.sort(comparator, sortOrder != SortOrder.DESCENDING);
        }
    }

    /**
     * Returns the model from the associated grid rows.
     *
     * @return The model backing the associated grid rows. May be null.
     */
    private IListModel<Object> getModel() {
        Grid grid = getAncestor(Grid.class);
        Rows rows = grid == null ? null : grid.getRows();
        return rows == null ? null : rows.getModel(Object.class);
    }

    /**
     * Handles a sort request from the client.
     */
    @EventHandler(value = "sort", syncToClient = false)
    private void _sort() {
        toggleSort();
    }

    /**
     * Returns true if this is the currently sorted column. Note that this setting is mutually
     * exclusive among columns within the same grid instance.
     *
     * @return True if this is the currently sorted column.
     */
    @PropertyGetter("sortColumn")
    public boolean isSortColumn() {
        return sortColumn;
    }

    /**
     * When set to true, designates this column as the currently sorted column. Note that this
     * setting is mutually exclusive among columns within the same grid instance.
     *
     * @param sortColumn Set to true to sort this column and designate it as the current sort
     *            column. Doing so will set this property to false on all other columns.
     */
    @PropertySetter("sortColumn")
    public void setSortColumn(boolean sortColumn) {
        _setSortColumn(sortColumn, true);
    }

    /**
     * Sets the sort column state. If set to true, the column is sorted and designated as the
     * current sort column.
     *
     * @param sortColumn If true, this column is sorted and designated as the current sort column.
     * @param notifyParent If true, update the sort column property of the parent.
     */
    protected void _setSortColumn(boolean sortColumn, boolean notifyParent) {
        if (propertyChange("sortColumn", this.sortColumn, this.sortColumn = sortColumn, false)) {

            if (sortColumn) {
                sort();
            } else {
                updateClient();
            }

            if (notifyParent) {
                Columns parent = (Columns) getParent();

                if (parent != null) {
                    if (sortColumn) {
                        parent.setSortColumn(this);
                    } else if (parent.getSortColumn() == this) {
                        parent.setSortColumn(null);
                    }
                }
            }
        }
    }

    private void updateClient() {
        sync("sortOrder", sortComparator == null ? null : sortColumn ? sortOrder : SortOrder.UNSORTED);
    }
}

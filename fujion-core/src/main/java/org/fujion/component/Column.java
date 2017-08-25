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

    public Comparator<?> getSortComparator() {
        return sortComparator;
    }

    public void setSortComparator(Comparator<?> sortComparator) {
        if (sortComparator != this.sortComparator) {
            this.sortComparator = sortComparator;
            this.sortOrder = SortOrder.UNSORTED;
            updateClient();
        }
    }

    @PropertySetter("sortBy")
    public void setSortComparator(String propertyName) {
        setSortComparator(new SmartComparator(propertyName));
    }

    @PropertyGetter("sortOrder")
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    @PropertySetter("sortOrder")
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder == null ? SortOrder.UNSORTED : sortOrder;
    }

    @PropertyGetter("sortToggle")
    public SortToggle getSortToggle() {
        return sortToggle;
    }

    @PropertySetter("sortToggle")
    public void setSortToggle(SortToggle sortToggle) {
        this.sortToggle = sortToggle;
    }

    public void toggleSort() {
        int i = sortOrder.ordinal() + 1;
        int max = sortToggle == SortToggle.TRISTATE ? 3 : 2;
        setSortOrder(SortOrder.values()[i >= max ? 0 : i]);
        sort();
    }

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

    private IListModel<Object> getModel() {
        Grid grid = getAncestor(Grid.class);
        Rows rows = grid == null ? null : grid.getRows();
        return rows == null ? null : rows.getModelAndView(Object.class).getModel();
    }

    @EventHandler(value = "sort", syncToClient = false)
    private void _sort() {
        toggleSort();
    }

    @PropertyGetter("sortColumn")
    public boolean isSortColumn() {
        return sortColumn;
    }

    @PropertySetter("sortColumn")
    public void setSortColumn(boolean sortColumn) {
        _setSortColumn(sortColumn, true);
    }

    protected void _setSortColumn(boolean sortColumn, boolean notifyParent) {
        if (sortColumn != this.sortColumn) {
            this.sortColumn = sortColumn;

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

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

import org.fujion.ancillary.ComponentException;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.model.IPaginator;
import org.fujion.model.IPaginator.IPagingListener;
import org.fujion.model.ISupportsModel;

/**
 * A page navigation component.
 */
@Component(tag = "paging", widgetClass = "Paging", parentTag = "*")
public class Paging extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionNone> {
    
    private IPaginator paginator;
    
    private int currentPage;

    private int pageSize;
    
    private int maxPage;
    
    private boolean fromPaginator;

    private final IPagingListener pagingListener = (type, oldValue, newValue) -> {
        try {
            fromPaginator = true;
            
            switch (type) {
                case CURRENT_PAGE:
                    setCurrentPage(newValue);
                    break;

                case PAGE_SIZE:
                    setPageSize(newValue);
                    break;

                case MAX_PAGE:
                    setMaxPage(newValue);
                    break;
            }
        } finally {
            fromPaginator = false;
        }
    };

    public Paging() {
        this(null);
    }
    
    public Paging(String label) {
        super(label);
    }

    /**
     * Returns the paginator used by this component.
     *
     * @return The paginator used by this component.
     */
    public IPaginator getPaginator() {
        return paginator;
    }

    /**
     * Sets the paginator used by this component.
     *
     * @param paginator The paginator used by this component.
     */
    public void setPaginator(IPaginator paginator) {
        if (paginator != this.paginator) {
            if (this.paginator != null) {
                this.paginator.removeEventListener(pagingListener);
            }

            this.paginator = paginator;
            setMaxPage(paginator == null ? 0 : paginator.getMaxPage());
            syncToPaginator();
        }
    }
    
    /**
     * Returns the number of the currently selected page.
     *
     * @return The number of the currently selected page.
     */
    @PropertyGetter("currentPage")
    public int getCurrentPage() {
        return currentPage;
    }
    
    /**
     * Sets the number of the currently selected page.
     *
     * @param currentPage The number of the currently selected page.
     */
    @PropertySetter("currentPage")
    public void setCurrentPage(int currentPage) {
        if (propertyChange("currentPage", this.currentPage, this.currentPage = currentPage, true)) {
            syncToPaginator();
        }
    }
    
    /**
     * Returns the maximum number of items on a single page.
     *
     * @return The maximum number of items on a single page.
     */
    @PropertyGetter("pageSize")
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * Sets the maximum number of items on a single page.
     *
     * @param pageSize The maximum number of items on a single page.
     */
    @PropertySetter("pageSize")
    public void setPageSize(int pageSize) {
        if (propertyChange("pageSize", this.pageSize, this.pageSize = pageSize, true)) {
            syncToPaginator();
        }
    }

    /**
     * Sets the component whose associated model will be manipulated by paging operations.
     *
     * @param comp A component that implements the {@link ISupportsModel} interface.
     * @exception ComponentException Thrown if the specified component does not support the
     *                {@link ISupportsModel} interface.
     */
    @PropertySetter(value = "target", defer = true)
    private void setPagingTarget(BaseComponent comp) {
        if (comp == null) {
            setPaginator(null);
        } else if (comp instanceof ISupportsModel) {
            setPaginator(((ISupportsModel<?>) comp).getPaginator());
        } else {
            throw new ComponentException(comp, "Paging target does not support model");
        }
    }
    
    private void setMaxPage(int maxPage) {
        propertyChange("maxPage", this.maxPage, this.maxPage = maxPage, true);
    }
    
    /**
     * Sync settings from this component with those of the paginator.
     */
    private void syncToPaginator() {
        if (paginator != null && !fromPaginator) {
            paginator.removeEventListener(pagingListener);
            paginator.setPageSize(pageSize);
            paginator.setCurrentPage(currentPage);
            paginator.addEventListener(pagingListener);
        }
    }

    /**
     * Handles change event from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false)
    private void _onChange(ChangeEvent event) {
        currentPage = defaultify(event.getValue(Integer.class), currentPage);
        syncToPaginator();
    }
}

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
package org.fujion.model;

/**
 * Support for model sorting.
 */
public class Sorting {

    /**
     * Ordering specifier for sort operation.
     */
    public enum SortOrder {
        ASCENDING, DESCENDING, NATIVE, UNSORTED;
    }

    /**
     * Type of sort toggle.
     */
    public enum SortToggle {
        BINARY, TRISTATE;
    }

    private Sorting() {
    }
}

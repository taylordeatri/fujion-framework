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
package org.fujion.expression;

import java.util.Map;

import org.fujion.component.BaseComponent;
import org.fujion.page.PageElement;

/**
 * This serves as the context root for an EL expression evaluation.
 */
public class ELContext {
    
    private final BaseComponent component;
    
    private final BaseComponent parent;
    
    private final PageElement element;

    private final Map<String, Object> args;
    
    public ELContext(BaseComponent component, BaseComponent parent, PageElement element, Map<String, Object> args) {
        this.component = component;
        this.parent = parent;
        this.element = element;
        this.args = args;
    }
    
    public Object getValue(String name) {
        Object result = "self".equals(name) ? component : element.getTagLibrary(name);
        result = result != null ? result : args == null ? null : args.get(name);
        result = result != null ? result : component.getAttribute(name);
        result = result != null ? result : parent == null ? null : parent.findByName(name);
        return result;
    }
}

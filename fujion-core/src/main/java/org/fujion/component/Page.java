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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.fujion.common.WeakMap;
import org.fujion.ancillary.ComponentException;
import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.client.ClientRequest;
import org.fujion.client.Synchronizer;
import org.fujion.core.WebUtil;
import org.fujion.event.Event;
import org.fujion.event.EventQueue;
import org.fujion.page.PageRegistry;
import org.fujion.websocket.Session;

/**
 * This is the root component of the component hierarchy.
 */
@Component(tag = "page", widgetClass = "Page", content = ContentHandling.AS_CHILD, childTag = @ChildTag("*"))
public final class Page extends BaseComponent implements INamespace {

    public static final String ID_PREFIX = "_fujion_";

    private static final AtomicInteger uniqueId = new AtomicInteger();

    private Synchronizer synchronizer;

    private Session session;

    private int nextId;

    private final Map<String, BaseComponent> ids = new WeakMap<>();

    private final EventQueue eventQueue = new EventQueue(this);

    private final Map<String, Object> browserInfo = new HashMap<>();

    private Map<String, String> queryParams;

    private String title;

    private final String src;

    public static Page _create(String src) {
        return new Page(src);
    }

    @SuppressWarnings("unchecked")
    public static void _init(Page page, ClientRequest request, Synchronizer synchronizer) {
        page.synchronizer = synchronizer;
        page.session = request.getSession();
        page.browserInfo.putAll((Map<String, Object>) request.getData());
        page._attach(page);
    }

    public Page() {
        src = null;
    }

    private Page(String src) {
        this._setId(ID_PREFIX + Integer.toHexString(uniqueId.incrementAndGet()));
        this.src = src;
        PageRegistry.registerPage(this);
    }

    public Synchronizer getSynchronizer() {
        return synchronizer;
    }

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    @Override
    public void setParent(BaseComponent parent) {
        throw new ComponentException(this, "Page cannot have a parent.");
    }

    public String getBrowserInfo(String key) {
        Object value = browserInfo.get(key);
        return value == null ? null : value.toString();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBrowserInfo(String key, Class<T> clazz) {
        return (T) browserInfo.get(key);
    }

    public Map<String, Object> getBrowserInfo() {
        return Collections.unmodifiableMap(browserInfo);
    }

    public String getQueryParam(String param) {
        return getQueryParams().get(param);
    }

    public Map<String, String> getQueryParams() {
        if (queryParams == null) {
            String requestUrl = (String) browserInfo.get("requestURL");
            int i = requestUrl == null ? -1 : requestUrl.indexOf("?");

            if (i >= 0) {
                queryParams = WebUtil.queryStringToMap(requestUrl.substring(i + 1), ",");
            } else {
                queryParams = Collections.emptyMap();
            }
        }

        return Collections.unmodifiableMap(queryParams);
    }

    public Session getSession() {
        return session;
    }

    public String getSrc() {
        return src;
    }

    private String nextComponentId() {
        return getId() + "_" + Integer.toHexString(++nextId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PageRegistry.unregisterPage(this);
        synchronizer.clear();
        eventQueue.clearAll();
        session = null;
    }

    /*package*/ void registerComponent(BaseComponent component, boolean register) {
        String id = component.getId();

        if (id == null) {
            id = nextComponentId();
            component._setId(id);
        }

        if (register) {
            ids.put(id, component);
        } else {
            ids.remove(id);
        }

        Event event = new Event(register ? "register" : "unregister", this, component);
        fireEvent(event);
    }

    public BaseComponent findById(String id) {
        int i = id.indexOf('-');
        return ids.get(i == -1 ? id : id.substring(0, i));
    }

    @PropertyGetter("title")
    public String getTitle() {
        return title;
    }

    @PropertySetter("title")
    public void setTitle(String title) {
        if (!areEqual(title = nullify(title), this.title)) {
            sync("title", this.title = title);
        }
    }
}

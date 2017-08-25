package org.fujion.test;

import org.fujion.component.Page;
import org.fujion.websocket.Session;

public class MockSession extends Session {
    
    public MockSession(MockServletContext servletContext, MockWebSocketSession socket) {
        super(servletContext, socket);
        Page page = Page._create("mockpage");
        _init(page.getId());
    }
    
    @Override
    protected void destroy() {
        super.destroy();
    }
}

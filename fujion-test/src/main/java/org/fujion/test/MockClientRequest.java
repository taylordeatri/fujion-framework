package org.fujion.test;

import java.util.Map;

import org.fujion.client.ClientRequest;

public class MockClientRequest extends ClientRequest {
    
    public MockClientRequest(MockSession session, Map<String, Object> map) {
        super(session, map);
    }
    
}

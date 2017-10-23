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
package org.fujion.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.core.io.Resource;

/**
 * Base class for tests using mock environment. Use this when you don't need a running web server
 * for unit tests.
 */
public class MockTest {
    
    public static Class<? extends MockEnvironment> mockEnvironmentClass = MockEnvironment.class;
    
    public static MockConfig rootConfig = new MockConfig(
            new String[] { "classpath:/META-INF/fujion-dispatcher-servlet.xml" }, null);
    
    public static MockConfig childConfig;
    
    private static MockEnvironment mockEnvironment;

    private static int initCount;
    
    @BeforeClass
    public static void beforeClass() throws Exception {
        initCount++;
        getMockEnvironment();
    }
    
    @AfterClass
    public static void afterClass() {
        initCount = initCount <= 0 ? 0 : initCount - 1;

        if (initCount == 0 && mockEnvironment != null) {
            System.out.println("Destroying mock environment...");
            mockEnvironment.close();
            mockEnvironment = null;
        }
    }
    
    /**
     * Returns the mock environment, instantiating it if necessary.
     *
     * @return The mock environment.
     */
    public static MockEnvironment getMockEnvironment() {
        if (mockEnvironment == null) {
            try {
                System.out.println("Initializing mock environment...");
                mockEnvironment = mockEnvironmentClass.newInstance();
                mockEnvironment.init(rootConfig, childConfig);
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
        
        return mockEnvironment;
    }
    
    /**
     * Reads text from the specified resource on the classpath.
     *
     * @param resourceName Name of the resource.
     * @return Text read from the resource.
     * @throws IOException IO exception.
     */
    public static String getTextFromResource(String resourceName) throws IOException {
        Resource resource = getMockEnvironment().getRootContext().getResource("classpath:" + resourceName);
        InputStream is = resource.getInputStream();
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StrUtil.UTF8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
    }
}

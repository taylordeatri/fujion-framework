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
package org.fujion.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fujion.component.Div;
import org.fujion.event.KeyCode;
import org.junit.Test;

public class Tests {
    
    private static final String ATTR_TEST = "ATTR_TEST";

    private static final String ATTR_OBJECT = "ATTR_OBJECT";

    private static final String ATTR_NULL = "ATTR_NULL";

    @Test
    public void keyCodeTest() {
        assertEquals(KeyCode.VK_BACK_SPACE, KeyCode.fromCode(8));
        assertEquals(KeyCode.VK_ASTERISK, KeyCode.fromString("ASTERISK"));
        assertEquals(KeyCode.normalizeKeyCapture("^A ~F1 ^@~@^$1"), "^#65 ~#112 ^@~$#49");
    }
    
    @Test
    public void attributeTests() {
        Div cmpt = new Div();
        cmpt.setAttribute(ATTR_OBJECT, new Object());
        cmpt.setAttribute(ATTR_TEST, 1234);
        assertTrue(1234 == cmpt.getAttribute(ATTR_TEST, 0));
        assertTrue(4321 == cmpt.getAttribute(ATTR_OBJECT, 4321));
        assertTrue(5678 == cmpt.getAttribute(ATTR_NULL, 5678));
        cmpt.setAttribute(ATTR_TEST, true);
        assertTrue(cmpt.getAttribute(ATTR_TEST, Boolean.class));
        cmpt.setAttribute(ATTR_TEST, "TRUE");
        assertTrue(cmpt.getAttribute(ATTR_TEST, Boolean.class));
        cmpt.setAttribute(ATTR_TEST, "ANYTHING BUT TRUE");
        assertNull(cmpt.getAttribute(ATTR_TEST, Boolean.class));
        assertNull(cmpt.getAttribute(ATTR_OBJECT, Boolean.class));
        assertNull(cmpt.getAttribute(ATTR_NULL, Boolean.class));
        cmpt.setAttribute(ATTR_TEST, ATTR_TEST);
        assertEquals(ATTR_TEST, cmpt.getAttribute(ATTR_TEST, String.class));
        assertTrue(cmpt.getAttribute(ATTR_OBJECT, String.class).contains("Object"));
        assertNull(cmpt.getAttribute(ATTR_NULL, String.class));
        List<Boolean> list = new ArrayList<>();
        cmpt.setAttribute(ATTR_TEST, list);
        assertSame(list, cmpt.getAttribute(ATTR_TEST, List.class));
        assertNotNull(cmpt.getAttribute(ATTR_OBJECT));
        assertNull(cmpt.getAttribute(ATTR_NULL));
        cmpt.setAttribute(ATTR_TEST, cmpt);
        assertSame(cmpt, cmpt.getAttribute(ATTR_TEST, Div.class));
        assertNull(cmpt.getAttribute(ATTR_OBJECT, Div.class));
    }

}

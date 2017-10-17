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
package org.fujion.ancillary;

import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;
import org.fujion.common.MiscUtil;

/**
 * Stores a method invocation to be executed at a later time.
 *
 * @param <T> Return type of the method invocation.
 */
public class DeferredInvocation<T> {

    private final Object instance;

    private final Method method;

    private final Object[] defaultArgs;
    
    /**
     * Create a deferred execution.
     *
     * @param instance Instance that is the target of the execution.
     * @param method The method to be executed.
     * @param defaultArgs Optional default argument list.
     */
    public DeferredInvocation(Object instance, Method method, Object... defaultArgs) {
        this.instance = instance;
        this.method = method;
        this.defaultArgs = defaultArgs;
    }

    /**
     * Invoke the deferred method.
     *
     * @param args Arguments to pass to the method (if not specified, the default arguments will be
     *            used).
     * @return Value returned by the method.
     */
    @SuppressWarnings("unchecked")
    public T invoke(Object... args) {
        try {
            Object[] arguments = ArrayUtils.addAll(defaultArgs, args);
            return (T) ConvertUtil.invokeMethod(instance, method, arguments);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
}

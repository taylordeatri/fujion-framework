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
package org.fujion.page;

/**
 * Run time exception related to parsing errors.
 */
public class ParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ParserException(String message, Object... args) {
        super(String.format(message, args));
    }
    
    public ParserException(Throwable cause, String message, Object... args) {
        super(String.format(message, args) + ": " + cause.getMessage());
    }
    
}

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
package org.fujion.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.fujion.core.WebUtil;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

/**
 * Checks for the presence of a minified version of a resource, returning it instead if found.
 * Enabling debug mode will disable this resolver..
 */
public class MinifiedResourceResolver extends AbstractResourceResolver {

    private final boolean debugEnabled;

    private final String[] extensions;

    /**
     * @param extensions The file extensions to be considered.
     */
    public MinifiedResourceResolver(String... extensions) {
        this.extensions = extensions;
        debugEnabled = WebUtil.isDebugEnabled();
    }

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
                                               List<? extends Resource> locations, ResourceResolverChain chain) {
        boolean ignore = !FilenameUtils.isExtension(requestPath, extensions);

        if (!ignore) {
            int i = requestPath.lastIndexOf(".");
            String ext = debugEnabled ? ".src" : ".min";
            String path = requestPath.substring(0, i) + ext + requestPath.substring(i);
            Resource resource = chain.resolveResource(request, path, locations);

            if (resource != null) {
                return resource;
            }
        }

        return chain.resolveResource(request, requestPath, locations);
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
                                            ResourceResolverChain chain) {
        return chain.resolveUrlPath(resourceUrlPath, locations);
    }

}

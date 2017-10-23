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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.annotation.ComponentScanner;
import org.fujion.annotation.EventTypeScanner;
import org.fujion.component.BaseComponent;
import org.fujion.event.Event;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

/**
 * Main servlet configurator.
 */
@EnableWebMvc
@Configuration
public class ServletConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    
    private static final Log log = LogFactory.getLog(ServletConfiguration.class);

    private final GzipResourceResolver gzipResourceResolver = new GzipResourceResolver();

    private final ResourceResolver contentVersionResolver = new VersionResourceResolver().addContentVersionStrategy("/**");

    private final ResourceResolver webjarResourceResolver = new WebJarResourceResolver();

    private final FujionResourceTransformer fujionResourceTransformer = new FujionResourceTransformer();

    private final AppCacheManifestTransformer appCacheManifestTransformer = new AppCacheManifestTransformer();

    private final MinifiedResourceResolver minifiedResourceResolver = new MinifiedResourceResolver("js", "css");

    private ApplicationContext applicationContext;

    public ServletConfiguration() {
        ComponentScanner.getInstance().scanPackage(BaseComponent.class.getPackage());
        EventTypeScanner.getInstance().scanPackage(Event.class.getPackage());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("fsp", MediaType.TEXT_HTML);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        addResourceHandlers(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/", webjarResourceResolver);
        addResourceHandlers(registry, "/web/**", "classpath:/web/", null);
        addResourceHandlers(registry, "/**", "/", contentVersionResolver);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        try {
            Resource[] resources = applicationContext.getResources("/index.*");

            if (resources.length > 0) {
                if (resources.length > 1) {
                    log.warn("Multiple home page candidates detected - only one will be selected.");
                }

                String file = "/" + resources[0].getFilename();
                registry.addRedirectViewController("/", file);
                log.info("Default home page set to: " + file);
            } else {
                log.info("No default home page detected.");
            }
        } catch (Exception e) {
            log.error("Error while attempting to detect default home page.", e);
        }
    }

    private void addResourceHandlers(ResourceHandlerRegistry registry, String pattern, String locations,
                                     ResourceResolver customResourceResolver) {
        //@formatter:off
        ResourceChainRegistration chain = registry
            .addResourceHandler(pattern)
            .addResourceLocations(locations)
            .resourceChain(false);

        if (customResourceResolver != null) {
            chain.addResolver(customResourceResolver);
        }

        chain
            .addResolver(minifiedResourceResolver)
            .addResolver(gzipResourceResolver)
            .addTransformer(fujionResourceTransformer)
            .addTransformer(appCacheManifestTransformer);
        //@formatter:on
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
    
}

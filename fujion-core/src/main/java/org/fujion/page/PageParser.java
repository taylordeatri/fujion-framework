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

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.ancillary.ComponentRegistry;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.common.RegistryMap;
import org.fujion.common.RegistryMap.DuplicateAction;
import org.fujion.component.Content;
import org.fujion.core.WebUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * Parses a Fujion server page into a page definition.
 */
public class PageParser implements BeanPostProcessor {
    
    private static final Log log = LogFactory.getLog(PageParser.class);
    
    private static final PageParser instance = new PageParser();
    
    private static final String CONTENT_ATTR = "#text";
    
    private static final String NS_FSP = "http://www.fujion.org/fsp";
    
    private static final String NS_ON = "http://www.fujion.org/fsp/on";
    
    private static final String NS_ATTR = "http://www.fujion.org/fsp/attr";
    
    private static RegistryMap<String, PIParserBase> piParsers = new RegistryMap<>(DuplicateAction.ERROR);
    
    public static PageParser getInstance() {
        return instance;
    }
    
    private PageParser() {
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param src URL of the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(String src) {
        return parse(WebUtil.getResource(src));
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param resource The resource containing the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(Resource resource) {
        return parse(new PageSource(resource));
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param stream An input stream referencing the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(InputStream stream) {
        return parse(new PageSource(stream));
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param source Source of the FSP.
     * @return The resulting page definition.
     */
    protected PageDefinition parse(PageSource source) {
        PageDefinition pageDefinition = new PageDefinition();
        pageDefinition.setSource(source.getSource());
        parse(source, pageDefinition.getRootElement());
        return pageDefinition;
    }
    
    /**
     * Parse the FSP document referenced by an input stream.
     *
     * @param source Source of the FSP.
     * @param parentElement The parent element for the parsing operation.
     */
    protected void parse(PageSource source, PageElement parentElement) {
        parseNode(source.getDocument(), parentElement);
    }

    private void parseNode(Node node, PageElement parentElement) {
        ComponentDefinition def;
        PageElement childElement;
        
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                Element ele = (Element) node;
                String tag = ele.getTagName();

                if (tag.equals("fsp") && node.getParentNode() instanceof Document) {
                    parseChildren(node, parentElement);
                    return;
                }

                def = ComponentRegistry.getInstance().get(tag);
                
                if (def == null) {
                    throw new ParserException("Unrecognized tag  '<%s>'", tag);
                }
                
                childElement = new PageElement(def, parentElement);
                NamedNodeMap attributes = ele.getAttributes();
                
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attr = attributes.item(i);
                    String name = attr.getNodeName();
                    
                    if (!name.startsWith("xml")) {
                        childElement.setAttribute(name, attr.getNodeValue());
                    }
                }
                
                parseChildren(node, childElement);
                childElement.validate();
                break;
            
            case Node.TEXT_NODE:
            case Node.CDATA_SECTION_NODE:
                String value = ((Text) node).getWholeText();
                
                if (value.trim().isEmpty()) {
                    break;
                }
                
                ComponentDefinition parentDef = parentElement.getDefinition();
                
                switch (parentDef == null ? ContentHandling.AS_CHILD : parentDef.contentHandling()) {
                    case ERROR:
                        throw new ParserException("Text content is not allowed for tag '<%s>'", parentDef.getTag());
                        
                    case IGNORE:
                        break;
                    
                    case AS_ATTRIBUTE:
                        parentElement.setAttribute(CONTENT_ATTR, normalizeText(value));
                        break;
                    
                    case AS_CHILD:
                        def = ComponentRegistry.getInstance().get(Content.class);
                        childElement = new PageElement(def, parentElement);
                        childElement.setAttribute(CONTENT_ATTR, normalizeText(value));
                        break;
                }
                
                break;
            
            case Node.DOCUMENT_NODE:
                parseChildren(node, parentElement);
                break;
            
            case Node.COMMENT_NODE:
                break;
            
            case Node.PROCESSING_INSTRUCTION_NODE:
                ProcessingInstruction pi = (ProcessingInstruction) node;
                PIParserBase piParser = piParsers.get(pi.getTarget());
                
                if (piParser != null) {
                    piParser.parse(pi, parentElement);
                } else {
                    throw new ParserException("Unrecognized processing instruction '%s'", pi.getTarget());
                }
                
                break;
            
            default:
                throw new ParserException("Unrecognized document content type '%s'", node.getNodeName());
        }
    }
    
    private void parseChildren(Node node, PageElement parentElement) {
        NodeList children = node.getChildNodes();
        int childCount = children.getLength();
        
        for (int i = 0; i < childCount; i++) {
            Node childNode = children.item(i);
            parseNode(childNode, parentElement);
        }
    }
    
    private String normalizeText(String text) {
        int i = text.indexOf('\n');
        
        if (i == -1) {
            return text;
        }
        
        if (text.substring(0, i).trim().isEmpty()) {
            text = text.substring(i + 1);
        }
        
        i = text.lastIndexOf('\n');
        
        if (i >= 0 && text.substring(i).trim().isEmpty()) {
            text = text.substring(0, i);
        }
        
        return text;
    }

    /**
     * Registers a processing instruction parser.
     *
     * @param piParser A processing instruction parser.
     */
    private void registerPIParser(PIParserBase piParser) {
        piParsers.put(piParser.getTarget(), piParser);
        log.info("Registered processing instruction parser for target '" + piParser.getTarget() + "'.");
    }
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PIParserBase) {
            registerPIParser((PIParserBase) bean);
        }
        
        return bean;
    }
    
}

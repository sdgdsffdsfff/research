/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.XSLT;

import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * 在java bean和xml之间相互转换
 * @author dengqb
 * @date 2015年6月5日
 */
public class XMLParser {

    /**
     * xml to java bean
     * @param xmlPath path of the desired resource
     * @param classesToBeBound list of java classes to be recognized by the new JAXBContext. Can be empty, in which case a JAXBContext that only knows about spec-defined classes will be returned.
     * @return 解析后实体集合
     * @throws JAXBException
     * @throws IOException
     */
    public static Object xmlToJavaParse(String xmlPath, Class<?>... classesToBeBound) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(classesToBeBound);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        URL url = XMLParser.class.getResource(xmlPath);
        Object object = unmarshaller.unmarshal(url.openStream());
        return object;
    }
    
}

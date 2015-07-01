package com.camel.XSLT.transform;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 使用xslt tansformer依据xsl将xml转成其他格式
 * 
 * @author dengqb
 * @date 2015年6月9日
 */
public class XsltTransformer {
    
    public static void Transform (String xmlPath,String xslPath, OutputStream out) throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        
        javax.xml.transform.Transformer transformer = tFactory.newTransformer(new StreamSource(xslPath));
        
        transformer.transform(new StreamSource(xmlPath), new StreamResult(out));
    }
    
}

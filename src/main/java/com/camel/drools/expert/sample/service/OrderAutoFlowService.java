/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.service;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilderErrors;
import org.drools.compiler.lang.dsl.DSLMappingFile;
import org.drools.compiler.lang.dsl.DSLTokenizedMappingFile;
import org.drools.compiler.lang.dsl.DefaultExpander;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.camel.drools.expert.sample.domain.OrderAutoFlowRule;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 订单自动流转规则处理
 * @author dengqb
 * @date 2014年8月21日
 */
public class OrderAutoFlowService {

    @Autowired
    private InternalKnowledgeBase kBase;
    
    private String drlStr;
    
    private String dslPath;
    
    private String dslFtlName;
    
    public String getDrlStr() {
        return drlStr;
    }

    public void setDrlStr(String drlStr) {
        this.drlStr = drlStr;
    }

    public String getDslPath() {
        return dslPath;
    }

    public void setDslPath(String dslPath) {
        this.dslPath = dslPath;
    }

    public String getDslFtlName() {
        return dslFtlName;
    }

    public void setDslFtlName(String dslFtlName) {
        this.dslFtlName = dslFtlName;
    }

    /**
     * 获取FreeMarker Template
     * 场景：成功
     * @return 
     */
    public Template getTemplate(Configuration cfg, String ftlFile) {
        Template temp = null;
        try {
            temp = cfg.getTemplate(ftlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 由FreeMarker文件生成drools dslr文件
     * @param oafr 
     */
    public String generDslrFromFtlFile(OrderAutoFlowRule oafr, Template temp) {
        Map root = new HashMap();
        root.put("orderAutoFlowRule", oafr);
//        Writer out = new OutputStreamWriter(System.out);
        Writer out = new StringWriter();
        try {
            temp.process(root, out);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
    
    /**
     * 由DSL和DSLR生成DRL
     * @param dslrReader
     * @param dslReader
     * @return
     */
    public String getDrlFromDsl(Reader dslrReader, Reader dslReader) {
        DSLMappingFile dslFile = new DSLTokenizedMappingFile();
        String drl = "";
        try {
            final boolean parseResult = dslFile.parseAndLoad(dslReader);
            dslReader.close();
            DefaultExpander expander = new DefaultExpander();
            expander.addDSLMapping(dslFile.getMapping());
            drl = expander.expand(dslrReader);
            dslrReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drl;
    }

    /**
     * 获取Kbase，如果没有则创建
     * @param drlStr
     * @return
     */
    public InternalKnowledgeBase getKBase() {
        if (kBase == null){
            kBase = createKBase(drlStr);
        }
        return kBase;
    }
    
    /**
     * 创建kBase
     * @param drlStr
     * @return
     */
    public InternalKnowledgeBase createKBase(String drlStr) {
        kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        KnowledgeBuilderImpl builder = new KnowledgeBuilderImpl();
        try {
            builder.addPackageFromDrl(new StringReader(drlStr));
            boolean hasError = builder.hasErrors();
            if(hasError){
                PackageBuilderErrors errors = builder.getErrors();
                for (int i=0; i<errors.size();i++){
                    System.out.println(errors.get(i).toString());
                }
                throw new RuntimeException("building drools package errors");
            }
        } catch (DroolsParserException | IOException e) {
            e.printStackTrace();
        }
        kBase.addPackage(builder.getPackage());
        
        return kBase;
    }
    
}

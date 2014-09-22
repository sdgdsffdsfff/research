/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.drools.compiler.lang.dsl.DSLMappingFile;
import org.drools.compiler.lang.dsl.DSLTokenizedMappingFile;
import org.drools.compiler.lang.dsl.DefaultExpander;
import org.drools.core.impl.InternalKnowledgeBase;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.camel.drools.expert.sample.domain.UserRule;
import com.camel.drools.expert.sample.utils.FMTemplateExceptionHandler;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 订单自动流转规则处理
 * @author dengqb
 * @date 2014年8月21日
 */
@Service
public class OrderAutoFlowService {
    @Resource
    private FreeMarkerConfigurer freemarkerConfig;
    
    private Template template;
    
    private InternalKnowledgeBase kBase = KBaseContext.getKBaseInstance();
    
    /**
     * DSL文件的路径
     */
    private String dslFilePath = "/drools/orderAutoFlowRules.dsl";
    
    /**
     * dslr freemarker template文件名称
     */
    private String dslrFtlName = "orderAutoFlowRules.dslr.ftl";
    /**
     * 存储生成的DRL的文件夹
     */
    private String drlDirPath = "/drools/drl/";
    
    public void createDrlFile(UserRule userRule){
        //获取freemarker 模板
        Template temp = getTemplate(dslrFtlName);
        //由freemarker模板生成dslr字符串
        String dslr = generDslrFromFtlFile(userRule,temp);
        System.out.println(dslr);
        final Reader dslReader = new InputStreamReader(this.getClass().getResourceAsStream(dslFilePath));
        
        String drl = getDrlFromDsl(new StringReader(dslr),dslReader);
        System.out.println(drl);
        File file = new File(KBaseContext.getClassPath()+drlDirPath+ userRule.getUserCode()+".drl");
        try {
            FileUtils.writeStringToFile(file, drl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取FreeMarker Template
     * @return 
     */
    public Template getTemplate(String ftlFile) {
        if (template == null){
            try {
                freemarkerConfig.getConfiguration().setTemplateExceptionHandler(new FMTemplateExceptionHandler());
                template = freemarkerConfig.getConfiguration().getTemplate(ftlFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return template;
    }

    /**
     * 由FreeMarker文件生成drools dslr文件
     * @param oafr 用户选择的规则参数对象
     */
    public String generDslrFromFtlFile(UserRule userRule, Template temp) {
        Map root = new HashMap();
        root.put("userRule", userRule);
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
            if (expander.hasErrors()){
                throw new RuntimeException("expanding drl from dslr error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drl;
    }
    
}

/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.service;

import static org.drools.compiler.compiler.DRLFactory.buildParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.DRL5Parser;
import org.drools.compiler.lang.DRLParser;
import org.drools.compiler.lang.descr.PackageDescr;
import org.drools.compiler.lang.dsl.DSLMappingFile;
import org.drools.compiler.lang.dsl.DSLTokenizedMappingFile;
import org.drools.compiler.lang.dsl.DefaultExpander;
import org.drools.compiler.rule.builder.RuleBuilder;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.definitions.impl.KnowledgePackageImpl;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.marshalling.impl.ProtobufMessages.KnowledgeSession;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.conf.LanguageLevelOption;
import org.kie.internal.io.ResourceFactory;

import com.camel.drools.expert.sample.domain.ERPCondition;
import com.camel.drools.expert.sample.domain.Item;
import com.camel.framework.utils.LogUtils;

/**
 * 
 * @author dengqb
 * @date 2014年8月18日
 */
public class EbayRelistingByRuleService {
    private static final String dslFile = "";
    
    private static final String dslrFile = "";
    
    /**
     * 初始化kBase
     * @return kBase
     */
    public InternalKnowledgeBase initKBase() {
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        return kBase;
    }

    /**
     * 获取dsl和dslr，合并成drl并生成package,方法一
     * @param dslFile dsl文件路径
     * @param dslrFile dslr文件路径
     * @return rule package
     */
    public InternalKnowledgePackage getRulePackage_1(final String dslPath, final String dslrPath) {
        LogUtils logUtils = new LogUtils();
        logUtils.beforeRun("read dsl");
        DSLMappingFile file = getDslMappingFileFromDslFile(dslPath);
        logUtils.afterRun("read dsl");
        
        logUtils = new LogUtils();
        logUtils.beforeRun("read dslr");
        final Reader dslrReader = readDslrFromFile(dslrPath);
        logUtils.afterRun("read dslr");
        
        logUtils = new LogUtils();
        logUtils.beforeRun("expand drl");
        final String drl = expandDslToStringDslr(file, dslrReader);
        logUtils.afterRun("expand drl");
        
        System.out.println(drl);
        PackageDescr packageDescr = null;
        
        logUtils = new LogUtils();
        logUtils.beforeRun("parse drl");
        DRLParser parser = getParser(drl);
        
        parser.enableEditorInterface();
        try {
            packageDescr = parser.compilationUnit();
        } catch (Exception ex) {
        }
        logUtils.afterRun("parse drl");
        
        logUtils = new LogUtils();
        logUtils.beforeRun("build package");
        final KnowledgeBuilderImpl builder = new KnowledgeBuilderImpl();
        builder.addPackage(packageDescr);
        
        InternalKnowledgePackage pkg = builder.getPackage();
        logUtils.afterRun("build package");
        return pkg;
    }
    
    /**
     * 获取dsl和dslr，合并成drl并生成package，方法二
     * @param dslFile dsl文件路径
     * @param dslrFile dslr文件路径
     * @return rule package
     */
    public InternalKnowledgePackage getRulePackage_2(final String dslPath, final String dslrPath) {
        LogUtils logUtils = new LogUtils();
        logUtils.beforeRun("build package");
        final KnowledgeBuilderImpl builder = new KnowledgeBuilderImpl();
        try {
            Reader drlReader = readDslrFromFile(dslrPath);
            Reader dslReader = readDslFromFile(dslPath);
            builder.addPackageFromDrl(drlReader, dslReader);
            
            drlReader.close();
            dslReader.close();
        } catch (DroolsParserException | IOException e) {
            e.printStackTrace();
        }
        InternalKnowledgePackage pkg = builder.getPackage();
        logUtils.afterRun("build package");
        return pkg;
    }
    
    /**
     * <pre>执行规则，找出匹配rule的item（需要relisting的item）</pre>
     * @param kBase InternalKnowledgeBase kBase
     * @return
     */
    public List<Item> getEbayRelistingItemsByRule(InternalKnowledgeBase kBase,List<Item> facts, ERPCondition cond) {
        LogUtils logUtils = new LogUtils();
        logUtils.beforeRun("execute rules");
        final KieSession kSession = kBase.newStatefulKnowledgeSession();
        List<Item> relistingItems = new ArrayList<Item>();
        kSession.setGlobal("relistingItems", relistingItems);
        
        for(Item item:facts){
            kSession.insert(item);
        }
        kSession.insert(cond);
        
        kSession.fireAllRules();
        logUtils.afterRun("execute rules");
        return relistingItems;
    } 
    
    /**
     * 从dsl文件读取dsl到DSLTokenizedMappingFile
     * @param fileName 文件名称，默认路径与当前类在同一目录下
     */
    private DSLMappingFile getDslMappingFileFromDslFile(String fileName){
        DSLMappingFile dslFile = new DSLTokenizedMappingFile();
        try {
            final Reader dslReader = readDslFromFile(fileName);
            //解析加载dsl文件
            final boolean parsingResult = dslFile.parseAndLoad(dslReader);
            dslReader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return dslFile;
    }
    
    private Reader readDslFromFile(String fileName) {
        final Reader dslReader = new InputStreamReader(this.getClass().getResourceAsStream(fileName));
        return dslReader;
    }
    
    /**
     * 从dslr文件读取dslr到Reader
     * @param fileName 文件名称，默认路径与当前类在同一目录下
     */
    private Reader readDslrFromFile(String fileName){
        final Reader dslrReader = new InputStreamReader(this.getClass().getResourceAsStream(fileName));
        return dslrReader;
    }
    
    /**
     * 将dsl扩展到组装的dsrl字符串，替换key值后，生成DRL Rule
     */
    private String expandDslToStringDslr(final DSLMappingFile file, final Reader dslrReader) {
        DefaultExpander de = new DefaultExpander();
        de.addDSLMapping(file.getMapping());
        
        //将DSL的逻辑映射替换DSLR中的dsl描述
        String drl = null;
        try {
            drl = de.expand(dslrReader);
            dslrReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drl;
    }
    
    
    /**
     * @return An instance of a RuleParser should you need one (most folks will
     *         not).
     */
    private DRLParser getParser(final String text) {
        return buildParser(text, LanguageLevelOption.DRL5);
    }

    /**
     * 重新刊登item
     * @param itemLst item list
     * @return int 刊登的数量
     */
    public int relistingItems(List<Item> itemLst) {
        int count = 0;
        for(Item item:itemLst){
            count ++;
        }
        return count;
    }

    
}

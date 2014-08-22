package inte.com.camel.drools.expert.dynamic;

import static org.drools.compiler.compiler.DRLFactory.buildParser;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.DRLParser;
import org.drools.compiler.lang.descr.PackageDescr;
import org.drools.compiler.lang.dsl.DSLMappingFile;
import org.drools.compiler.lang.dsl.DSLTokenizedMappingFile;
import org.drools.compiler.lang.dsl.DefaultExpander;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.impl.InternalKnowledgeBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.conf.LanguageLevelOption;
import org.kie.internal.io.ResourceFactory;

import com.camel.drools.expert.Applicant;
import com.camel.drools.expert.Application;

public class DSLTest {
    private static final String NL = System.getProperty("line.separator");
    
    @Before
    public void setUp() throws Exception {
        System.setProperty("drools.dateformat","yyyy-MM-dd");
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * 从文件读取dsl
     * 场景：成功
     */
    @Test
    public void readDslFromFile_Success(){
        DSLMappingFile dslFile = readDslFromFile("licenseApplication.dsl");
        assertTrue( dslFile.getErrors().size() == 0 );
        assertTrue( dslFile.getErrors().isEmpty() );
   
        assertEquals( 11,dslFile.getMapping().getEntries().size() );
    }
    
    /**
     * 将dsl扩展到组装的dsrl字符串，替换key值后，生成DRL Rule
     * 场景：成功
     */
    @Test
    public void expandDslToStringDslr_Success() {
        final DSLMappingFile dslFile = readDslFromFile("licenseApplication.dsl");
        final Reader dslrReader = readDslrFromFile("licenseApplication.dslr");
        final String drl = expandDslToStringDslr(dslFile, dslrReader);
        System.out.println(drl);
    }

    /**
     * 测试由dsl和dslr生成规则package
     * 场景：成功
     */
    @Test
    public void parseDslToDrlTest_Success(){
        InternalKnowledgeBase kBase = encapsulateKBaseWithRulePackage();
        
        //判断包含指定的package
        assertTrue (kBase.getPackagesMap().size() == 1);
        //判断包含2条规则
        assertEquals (2, kBase.getPackage("inte.com.camel.drools.expert").getRules().size());
    }

    /**
     * 测试规则执行
     * 场景：成功
     */
    @Test
    public void excuteRulesTest_Success(){
        InternalKnowledgeBase kBase = encapsulateKBaseWithRulePackage();
        
        //从kBase生成kiesession
        final KieSession workingMemory = kBase.newStatefulKnowledgeSession();
        
        //生成fact对象，需要传入drools规则引擎处理
        Applicant applicant = new Applicant("jordan", 16, "M");
        Application application = new Application(new Date(2014,8,2));
        List facts = new ArrayList();
        facts.add(applicant);
        facts.add(application);
        executeRules(facts, workingMemory);
    }
    
    /**
     * @return An instance of a RuleParser should you need one (most folks will
     *         not).
     */
    private DRLParser getParser(final String text) {
        return buildParser(text, LanguageLevelOption.DRL5);
    } 
    
    private InternalKnowledgeBase encapsulateKBaseWithRulePackage() {
        //读取DSL文件，生成 DSLMappingFile对象
        DSLMappingFile file = readDslFromFile("licenseApplication.dsl");
        
        //读取DSLR文件
        final Reader dslrReader = readDslrFromFile("licenseApplication.dslr");
        
        //扩展DSL到DSLR，生成Rule规则Drl
        String drl = expandDslToStringDslr(file, dslrReader);
        
        //从DRL生成pacakge
        InternalKnowledgePackage pkg = getRulePackageFromDrl(drl);
        
        //创建kBase对象
        InternalKnowledgeBase kBase = createKBase();
        
        //kBase添加规则包
        kBase.addPackage(pkg);
        return kBase;
    }
    
    /**
     * 从dsl文件读取dsl到DSLTokenizedMappingFile
     * @param fileName 文件名称，默认路径与当前类在同一目录下
     */
    private DSLMappingFile readDslFromFile(String fileName){
        DSLMappingFile dslFile = new DSLTokenizedMappingFile();
        final Reader dslReader = new InputStreamReader(this.getClass().getResourceAsStream(fileName));
        try {
            //解析加载dsl文件
            final boolean parsingResult = dslFile.parseAndLoad(dslReader);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return dslFile;
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
//        String rule = "rule xyz" + NL + 
//                "when" + NL +
//                "   applicant meet requirements of 18 \"M\"" + NL + 
//                "then" + NL + 
//                "   accept applicant \"bob\"" + NL + 
//                "end";
        
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
     * 从字符串drl构建rule package
     * @param drl
     * @return
     */
    private InternalKnowledgePackage getRulePackageFromDrl(final String drl) {
//        PackageDescr packageDescr = null;
//        DRLParser parser = getParser(drl);
//        parser.enableEditorInterface();
//        try {
//            packageDescr = parser.compilationUnit();
//        } catch (Exception ex) {
//        }
        
        final KnowledgeBuilderImpl builder = new KnowledgeBuilderImpl();
       // builder.addPackage(packageDescr);
        try {
            builder.addPackageFromDrl(new StringReader(drl));
        } catch (DroolsParserException | IOException e) {
            e.printStackTrace();
        }
        
        InternalKnowledgePackage pkg = builder.getPackage();
        return pkg;
    }
    
    /**
     * 创建KBase
     * @return
     */
    private InternalKnowledgeBase createKBase() {
        //创建kBase
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        return kBase;
    }
    
    
    /**
     * 执行规则
     * @param applicant
     * @param workingMemory
     */
    private void executeRules(List<Object> facts, final KieSession workingMemory) {
        
        //向session插入传入的对象/值
        for(int i =0 ;i<facts.size();i++){
            workingMemory.insert(facts.get(i));
        }
        
        System.out.println("fact count = "+workingMemory.getFactCount());
        //执行规则
        workingMemory.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
//                return match.getRule().getName().contains("xyz");
                return true;
            }
        });
    }
    
}

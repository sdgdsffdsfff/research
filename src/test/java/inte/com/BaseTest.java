package inte.com;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamManager;
import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import com.utils.AOPDynamicConfigurator.javassistaop.EnvConfigAOPManager;

/**
 * 不带事务的spring测试基类
 * 
 * @author dengqb
 * @date 2014年8月25日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml", "classpath:applicationContext-activemq-test.xml", "classpath:applicationContext-drools-test.xml" })
@Ignore
public class BaseTest extends AbstractJUnit4SpringContextTests {

    protected static final Integer ID = 2;
    protected final static String NICK = "sandbox_liuhui";
    protected final static Integer COMPANY_ID = 1;
    protected final static String MAIN_ACCOUNT = "this-just-junit-test";
    protected final static String SESSION_KEY = "6100f05450d931448bb576d3ddc17acbff5503a1b411e4e3594263856";

    @BeforeClass
    public static void setUp() throws Exception {
        ConfigParamManager cpm = new ConfigParamManager();
        cpm.readConfig();
        
        EnvConfigAOPManager.initAOP();
        System.out.println("active.mq.cec.wmc.order="+ConfigParamMap.getValue("active.mq.cec.wmc.order"));
    }
}

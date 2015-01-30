package inte.com;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamManager;
import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import com.utils.AOPDynamicConfigurator.javassistaop.EnvConfigAOPManager;

/**
 * 带事务的测试基类
 * 
 * @author Leo.du
 * @date 2013-8-4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback = false)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml", "classpath:applicationContext-activiti-test.xml"})
public class AbstractTransactionalSpringBaseTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @BeforeClass
    public static void setUp() throws Exception {
        ConfigParamManager cpm = new ConfigParamManager();
        cpm.readConfig();
        
        EnvConfigAOPManager.initAOP();
        System.out.println(ConfigParamMap.getValue("jdbc.url"));
        System.out.println("active.mq.cec.wmc.order="+ConfigParamMap.getValue("active.mq.cec.wmc.order"));
    }
}

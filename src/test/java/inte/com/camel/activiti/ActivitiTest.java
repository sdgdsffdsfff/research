package inte.com.camel.activiti;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import inte.com.AbstractTransactionalSpringBaseTest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.camel.activiti.domain.UserBean;

public class ActivitiTest extends AbstractTransactionalSpringBaseTest {
    
    @Resource
    private RepositoryService repositoryService;
    
    @Resource
    private UserBean userBean;
    
    @Resource
    private TaskService taskService;
    
    @Resource
    private RuntimeService runtimeService;
    
    @After
    public void tearDown() throws Exception {
    }

    private void loadBPMNxml(){
        repositoryService.createDeployment()
        .addClasspathResource("activiti/repository/hello.bpmn")
        .deploy()
        .getId();
    }
    
    @Test
    public void activititest() {
        //loadBPMNxml();
        
//        StopWatch stopWatch = new LoggingStopWatch("activiti started");
//        
//        ProcessInstance pi = runtimeService.startProcessInstanceByKey("helloProcess");
//        Execution execution = runtimeService.createExecutionQuery()
//                .processInstanceId(pi.getId())
//                .activityId("usertask1")
//                .singleResult();
//        System.out.println("execution id="+execution.getId());
//        ExecutionQuery eq = runtimeService.createExecutionQuery().processInstanceId(pi.getId());
//        List<Execution> exes = eq.list();
//        assertEquals(2,exes.size());
//        
//        assertEquals("usertask1",runtimeService.getActiveActivityIds(pi.getId()).get(0));
//        //runtimeService.signal(execution.getId());
//        stopWatch.stop();
        MainTest.main(null);
//        while(true){
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}

package inte.com.camel.newservicearch;

import static org.junit.Assert.*;
import inte.com.BaseTest;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.newservicearch.domain.Order;
import com.camel.newservicearch.service.OrderProcessService;

public class OrderProcessServiceTest extends BaseTest {
    @Resource
    private OrderProcessService orderProcessService;
    
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOrderProcess() {
        Order order = new Order();
        order.setAddress("");
        order.setSkuCode("");
        order.setOrderCD("TEST-01");
        order.setPrice(25.00D);
        order.setSkuNum(10);
        order.setTitle("this is test order");
        
        orderProcessService.orderProcess(order);
    }

}

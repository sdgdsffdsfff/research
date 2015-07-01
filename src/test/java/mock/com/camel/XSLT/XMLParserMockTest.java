package mock.com.camel.XSLT;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.XSLT.XMLParser;
import com.camel.XSLT.domain.Consignee;
import com.camel.XSLT.domain.Order;
import com.camel.XSLT.domain.Transaction;

public class XMLParserMockTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试xml转成java bean
     * 场景：成功
     */
    @Test
    public void testXmlToJavaParse_success() {
        String xmlPath= "/XSLT/xml/transaction.xml";
        try {
            Object object = XMLParser.xmlToJavaParse(xmlPath, Transaction.class);
            Transaction transaction = (Transaction)object;
            assertNotNull(transaction);
            System.out.println(transaction.getTransNo());
            for (Order order: transaction.getOrders()){
                System.out.println(order.getProductName() + order.getSellPrice() + order.getQuantity());
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJavaToXml_success() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Transaction.class);
        Marshaller marshaller = context.createMarshaller();
        
        Transaction trans = new Transaction();
        trans.setTransId("333");
        trans.setTransNo("qwe");
        
        List<Order> orders = new ArrayList<Order>();
        Order order = new Order();
        order.setProductName("jewels");
        order.setQuantity(1);
        order.setSellPrice(12.34);
        orders.add(order);
        
        Order order1 = new Order();
        order1.setProductName("necklack");
        order1.setQuantity(2);
        order1.setSellPrice(21.00);
        orders.add(order1);
        
        trans.setOrders(orders);
        
        Consignee consignee = new Consignee();
        consignee.setName("cameldeng");
        consignee.setAddress("shenzhen cfg");
        consignee.setPostCode("518100");
        
        trans.setConsignee(consignee);
        
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(trans, System.out);
    }
}

package mock.com.camel.XSLT;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.utils.FileUtils;


public class JsonXmlExchangerMockTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试从xml转成json
     * 场景：成功
     * @throws IOException 
     */
    @Test
    public void testXml2Json_success() throws IOException {
        String xmlPath= "/XSLT/xml/transaction.xml";
        XMLSerializer xs = new XMLSerializer();
        URL url = JsonXmlExchangerMockTest.class.getResource(xmlPath);
        JSON json = xs.readFromStream(url.openStream());
        assertNotNull(json);
        System.out.println(json.toString());
    }
    
    /**
     * 从json转成xml
     * 场景：成功
     * @throws IOException 
     */
    @Test
    public void testJson2Xml_success() throws IOException{
        String filePath = JsonXmlExchangerMockTest.class.getResource("/XSLT/xml/inputJson.txt").getPath();
        //FileUtils.readFileByLine(filePath);
        String jsonStr = FileUtils.readFileByLine(filePath);
        JSONObject jobj = JSONObject.fromObject(jsonStr);
        XMLSerializer xs = new XMLSerializer();
        xs.setRootName("transaction");
        String xml = xs.write(jobj);
        assertNotNull(xml);
        System.out.println(xml);
    }
    
    

}

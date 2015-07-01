package mock.com.camel.XSLT.transform;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.tools.ant.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.XSLT.transform.XsltTransformer;

public class XsltTransfomerMockTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 由xslt转换后的文档输出到console中
     * 场景：成功
     */
    @Test
    public void testXmlTransform2Console_success() {
        String xmlPath = XsltTransfomerMockTest.class.getResource("/XSLT/xml/cdcatalog.xml").getPath();
        String xslPath = XsltTransfomerMockTest.class.getResource("/XSLT/xml/cdcatalog.xsl").getPath();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            XsltTransformer.Transform(xmlPath, xslPath, os);
            assertTrue(os.size()>0);
            System.out.println(os.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * 由xslt转换后的文档持久化到本地
     * 场景：成功
     */
    @Test
    public void testXmlTransform2File_success() {
        String xmlPath = XsltTransfomerMockTest.class.getResource("/XSLT/xml/cdcatalog.xml").getPath();
        String xslPath = XsltTransfomerMockTest.class.getResource("/XSLT/xml/cdcatalog.xsl").getPath();
        try {
            String path = XsltTransfomerMockTest.class.getResource("/").getPath();
            String outputPath = path+"/XSLT/xml/cdcatalog.html";
            File outputFile = new File(outputPath);
            
            if (outputFile.exists()){
                outputFile.delete();
            }
            
            FileOutputStream os = new FileOutputStream(outputPath);
            XsltTransformer.Transform(xmlPath, xslPath, os);
            os.flush();
            os.close();
            
            assertTrue(outputFile.exists());
            
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package inte.com.camel.trainreserve;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.trainreserve.JDKHttpsClient;
import com.camel.utils.MD5;

public class JDKHttpsClientTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoGet() {
        fail("Not yet implemented");
    }

    @Test
    public void testDoGetImg() {
        String url = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&0.11995659543649284";
        
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(5);
        for (int i=0; i<5 ; i++ ) {
            sheduleJob(exec,url);
        }
    }
    
    private void sheduleJob(ScheduledExecutorService exec, final String url){
        exec.scheduleWithFixedDelay(new Runnable(){
            public void run(){
                ByteArrayOutputStream outStream = JDKHttpsClient.doGetImg(url);
                if (outStream.size() > 0){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    
                    File imageFile = new File(sdf.format(new Date())+".png");
                    
                    try {
                        FileOutputStream fos = new FileOutputStream(imageFile);
                        byte[] bytes = outStream.toByteArray();
                        fos.write(bytes);
                        MD5.GetMD5Code(bytes);
                        fos.close();
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(Thread.currentThread().getName() + " return empty");
                }
            }
        },0, 2, TimeUnit.SECONDS);
    }

}

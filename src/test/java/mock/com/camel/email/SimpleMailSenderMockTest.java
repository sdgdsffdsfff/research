package mock.com.camel.email;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.email.SimpleMailSender;

public class SimpleMailSenderMockTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSend() {
        SimpleMailSender  sender = new SimpleMailSender("smtp.exmail.qq.com","no_reply@channeleffect.com","@!q1w2e3r4t5");
        try {
            List<String> recipients = new ArrayList<String>();
            recipients.add("dengqb@4px.com");
            recipients.add("dqibiao@163.com");
            recipients.add("dqibiao@gmail.com");
            recipients.add("dqibiao@hotmail.com");
            
            sender.send(recipients, "思翼邀请码二", "CE invitation code: AATAT");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

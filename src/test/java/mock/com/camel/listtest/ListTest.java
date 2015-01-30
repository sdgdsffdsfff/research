package mock.com.camel.listtest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        List<String> testLst = new ArrayList<String>();
        
        for (String str: testLst){
            System.out.println(str);
        }
    }

}

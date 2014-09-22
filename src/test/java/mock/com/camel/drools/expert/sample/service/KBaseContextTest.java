/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package mock.com.camel.drools.expert.sample.service;

import static org.junit.Assert.*;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.drools.expert.sample.service.KBaseContext;

/**
 * 
 * @author dengqb
 * @date 2014年8月29日
 */
public class KBaseContextTest {
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.camel.drools.expert.sample.service.KBaseContext#getKBaseInstance()}.
     */
    @Test
    public void testGetKBaseInstance() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.camel.drools.expert.sample.service.KBaseContext#getDslProperties()}.
     */
    @Test
    public void testGetDslProperties() {
        Properties props = KBaseContext.getExprToSDOMapping();
        Set<Entry<Object,Object>> propSet = props.entrySet();
        
        for (Entry entry:propSet){
            System.out.println("key="+entry.getKey() +", value="+entry.getValue());
        }
        assertNotNull(props);
    }

}

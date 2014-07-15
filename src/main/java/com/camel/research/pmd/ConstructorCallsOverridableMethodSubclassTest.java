package com.camel.research.pmd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConstructorCallsOverridableMethodSubclassTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        ConstructorCallsOverridableMethodSubclass ccoms = new ConstructorCallsOverridableMethodSubclass();
        ccoms.toString();
    }

}

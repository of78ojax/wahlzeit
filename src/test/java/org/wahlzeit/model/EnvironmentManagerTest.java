package org.wahlzeit.model;
import java.lang.Double;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class EnvironmentManagerTest {
    @Test
    public void testManger(){
        Environment tmp =  EnvironmentManager.createEnvironment("test");
        assertTrue("Manager shoulkd have Environment just created", EnvironmentManager.hasID(tmp.getId()));
        assertEquals("Manager should return environment just created",tmp, EnvironmentManager.getEnvironment(tmp.getId()));
    }
}

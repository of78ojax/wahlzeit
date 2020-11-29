package org.wahlzeit.model;

import org.junit.Test;

import java.lang.Double;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class EnvironmentTest {
    @Test
    public void testValues() {
        assertTrue(Environment.DEFAULT.equalsName("None"));
        assertTrue(Environment.OCEAN.equalsName("Ocean"));
        assertTrue(Environment.MOUNTAINS.equalsName("Mountains"));
        assertTrue(Environment.FOREST.equalsName("Forest"));
        assertTrue(Environment.BEACH.equalsName("Beach"));
        assertTrue(Environment.URBAN.equalsName("Urban"));
        
        assertFalse(Environment.URBAN.equalsName("urban"));
        assertFalse(Environment.DEFAULT.equalsName("Ocean"));
    }
}

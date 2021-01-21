package org.wahlzeit.model;

import org.junit.Test;

import java.lang.Double;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class EnvironmentTest {
    @Test
    public void testValues() {
        EnvironmentType tmp = EnvironmentType.getEnvironmentType("test");
        Environment one = new Environment(tmp);
        Environment two = new Environment(tmp);

        assertNotEquals("Every created Environment should have distinct ID", one.getId(),two.getId());
        assertTrue("Environments should have the same type", one.haveSameType(two));

    }
}

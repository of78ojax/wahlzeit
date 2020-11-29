package org.wahlzeit.model;

import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentPhotoTest {
    @Test
    public void testEnvironmentDefaults(){
        EnvironmentPhoto eP = new EnvironmentPhoto();
        assertTrue("Default environemt should be None", eP.environment == Environment.DEFAULT);
        assertTrue(eP.hasSameEnvironment(eP));

        EnvironmentPhoto eP2 = new EnvironmentPhoto();
        eP2.environment = Environment.BEACH;
        assertFalse(eP.hasSameEnvironment(eP2));

    }
}

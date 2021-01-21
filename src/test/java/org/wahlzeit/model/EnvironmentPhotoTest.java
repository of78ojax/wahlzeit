package org.wahlzeit.model;

import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentPhotoTest {
    @Test
    public void testEnvironmentDefaults(){
        EnvironmentPhoto eP = new EnvironmentPhoto();
        assertTrue("Default environemt should be null", eP.environment == null);

        EnvironmentPhoto eP2 = new EnvironmentPhoto();
        eP.environment = EnvironmentManager.createEnvironment("Beache");
        assertFalse(eP.hasSameEnvironment(eP2));

    }
}

package org.wahlzeit.model;
import org.junit.Test;

import java.lang.Double;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentPhotoFactoryTest {
    @Test
    public void testFactoryOutput(){
        // didnt split this test mehtod because it is so short
        PhotoFactory fac = PhotoFactory.getInstance();
        assertTrue(fac instanceof EnvironmentPhotoFactory);
        Photo ph = fac.createPhoto();
        assertTrue(ph instanceof EnvironmentPhoto);
        ph = fac.createPhoto(PhotoId.getIdFromInt(120));
        assertTrue(ph instanceof EnvironmentPhoto);
    } 
    
}

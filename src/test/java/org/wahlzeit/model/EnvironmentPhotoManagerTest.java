package org.wahlzeit.model;

import org.junit.Test;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentPhotoManagerTest {
    
    @Test
    public void testInstance(){
        
        PhotoManager man = PhotoManager.getInstance();
        assertTrue(man instanceof EnvironmentPhotoManager);
    }
}

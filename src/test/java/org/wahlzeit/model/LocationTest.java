package org.wahlzeit.model;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LocationTest {
    @Test
    public void testLocationCOnstructor(){
        Location loc = new Location();
        assertEquals( loc.getLocationName(), "None");

        loc.setLocationName("NEW Location");
        assertEquals(loc.getLocationName(), "NEW Location");

    }

    @Test
    public void testLocationEqualAndHashCode(){
        Location loc = new Location();
        loc.setLocationName("NEW Location");
        loc.coordinate = new CartesianCoordinate(120,234,1241);
        Location loc2 = new Location();
        assertFalse("Sould be two different Locations", loc.equals(loc2));
        assertTrue("Sould be the same Location", loc.equals(loc));
        
        loc2.setLocationName("NEW Location");
        loc2.coordinate = new CartesianCoordinate(120,234,1241);
        assertEquals(loc.hashCode(), loc.hashCode());
        assertEquals(loc.hashCode(), loc2.hashCode());
        assertTrue("Sould be equal locations", loc.equals(loc2));
    }
}

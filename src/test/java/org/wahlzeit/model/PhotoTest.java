package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.EnvironmentPhoto.Environment;

import java.lang.Double;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PhotoTest {
    @Test
    public void testEnvironmentValues() {
        assertTrue(EnvironmentPhoto.Environment.DEFAULT.equalsName("None"));
        assertTrue(EnvironmentPhoto.Environment.OCEAN.equalsName("Ocean"));
        assertTrue(EnvironmentPhoto.Environment.MOUNTAINS.equalsName("Mountains"));
        assertTrue(EnvironmentPhoto.Environment.FOREST.equalsName("Forest"));
        assertTrue(EnvironmentPhoto.Environment.BEACH.equalsName("Beach"));
        assertTrue(EnvironmentPhoto.Environment.URBAN.equalsName("Urban"));
        
        assertFalse(EnvironmentPhoto.Environment.URBAN.equalsName("urban"));
        assertFalse(EnvironmentPhoto.Environment.DEFAULT.equalsName("Ocean"));
    }

    @Test
    public void testCoordinates(){
        Coordinate coordinate = new Coordinate();
        assertTrue("Default x-Value of Coords not correct", Double.compare(coordinate.getX(),1.0) == 0 );
        assertTrue("Default y-Value of Coords not correct", Double.compare(coordinate.getY(),2.0) == 0 );
        assertTrue("Default z-Value of Coords not correct", Double.compare(coordinate.getZ(),3.0) == 0 );
        coordinate = new Coordinate(4.0,0.5,14.3);
        assertTrue("initilizing constructors x-Value of coordinate not correct", Double.compare(coordinate.getX(),4.0) == 0 );
        assertTrue("initilizing constructors y-Value of coordinate not correct", Double.compare(coordinate.getY(),0.5) == 0 );
        assertTrue("initilizing constructors z-Value of coordinate not correct", Double.compare(coordinate.getZ(),14.3) == 0 );
        
        Coordinate a = new Coordinate(1.0,0.0,0.0);
        Coordinate b = new Coordinate(0.0,0.0,0.0);
        double distance = a.distance(b);
        assertTrue("distnace of simple coordinates is not correct", Double.compare(distance,1.0) == 0 );
        b = new Coordinate(1.0,3.0,4.0);
        assertFalse("Coordinates shouldnt be close", a.isClose(b));
        b = new Coordinate(1.03,0.0,0.0);
        assertTrue("Coordinates should be close", a.isClose(b));


        a = new Coordinate(1.0,4.0,8.03434);
        b = new Coordinate(1.0,4.0,8.03434);
        assertTrue("should be euqal", a.isEqual(b));

        a = new Coordinate(1.0402,4.0,8.03434);
        b = new Coordinate(1.0,4.0,8.03434);
        assertFalse("shouldnt be euqal", a.isEqual(b));
    }

    @Test
    public void testLocation(){
        Location loc = new Location();
        assertTrue("default location should be none", loc.getLocationName() == "None");
        assertTrue("default coords should be all -1.", loc.coordinate.isEqual(new Coordinate(-1.0,-1.0,-1.0)));

        loc.setLocationName("NEW Location");
        assertTrue("Setter not working", loc.getLocationName() == "NEW Location");

        loc.coordinate = new Coordinate(120,234,1241);
        Location loc2 = new Location();
        assertFalse("Sould be two different Locations", loc.equals(loc2));
        assertTrue("Sould be the same Location", loc.equals(loc));
        
        loc2.setLocationName("NEW Location");
        loc2.coordinate = new Coordinate(120,234,1241);
        assertTrue("Hash not consitent", loc.hashCode() == loc.hashCode());
        assertTrue("Not the same Hash", loc.hashCode() == loc2.hashCode());
        assertTrue("Sould be equal locations", loc.equals(loc2));
    }

    @Test
    public void testEnvironmentSpecialsation(){
        EnvironmentPhoto eP = new EnvironmentPhoto();
        assertTrue("Default environemt should be None", eP.environment == Environment.DEFAULT);
        assertTrue(eP.hasSameEnvironment(eP));

        EnvironmentPhoto eP2 = new EnvironmentPhoto();
        eP2.environment = Environment.BEACH;
        assertFalse(eP.hasSameEnvironment(eP2));
    }
}

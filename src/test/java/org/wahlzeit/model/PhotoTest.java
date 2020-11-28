package org.wahlzeit.model;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.lang.Double;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PhotoTest {
    @Test
    public void testEnvironmentValues() {
        assertTrue(Environment.DEFAULT.equalsName("None"));
        assertTrue(Environment.OCEAN.equalsName("Ocean"));
        assertTrue(Environment.MOUNTAINS.equalsName("Mountains"));
        assertTrue(Environment.FOREST.equalsName("Forest"));
        assertTrue(Environment.BEACH.equalsName("Beach"));
        assertTrue(Environment.URBAN.equalsName("Urban"));
        
        assertFalse(Environment.URBAN.equalsName("urban"));
        assertFalse(Environment.DEFAULT.equalsName("Ocean"));
    }

    @Test
    public void testCoordinates(){
        CartesianCoordinate coordinate = new CartesianCoordinate();
        assertTrue("Default x-Value of Coords not correct", Double.compare(coordinate.getX(),1.0) == 0 );
        assertTrue("Default y-Value of Coords not correct", Double.compare(coordinate.getY(),2.0) == 0 );
        assertTrue("Default z-Value of Coords not correct", Double.compare(coordinate.getZ(),3.0) == 0 );
        coordinate = new CartesianCoordinate(4.0,0.5,14.3);
        assertTrue("initilizing constructors x-Value of coordinate not correct", Double.compare(coordinate.getX(),4.0) == 0 );
        assertTrue("initilizing constructors y-Value of coordinate not correct", Double.compare(coordinate.getY(),0.5) == 0 );
        assertTrue("initilizing constructors z-Value of coordinate not correct", Double.compare(coordinate.getZ(),14.3) == 0 );
        
        CartesianCoordinate a = new CartesianCoordinate(1.0,0.0,0.0);
        CartesianCoordinate b = new CartesianCoordinate(0.0,0.0,0.0);
        double distance = a.distance(b);
        assertTrue("distnace of simple coordinates is not correct", Double.compare(distance,1.0) == 0 );
        b = new CartesianCoordinate(1.0,3.0,4.0);
        assertFalse("Coordinates shouldnt be close", a.isClose(b));
        b = new CartesianCoordinate(1.03,0.0,0.0);
        assertTrue("Coordinates should be close", a.isClose(b));


        a = new CartesianCoordinate(1.0,4.0,8.03434);
        b = new CartesianCoordinate(1.0,4.0,8.03434);
        assertTrue("should be euqal", a.isEqual(b));

        a = new CartesianCoordinate(1.0402,4.0,8.03434);
        b = new CartesianCoordinate(1.0,4.0,8.03434);
        assertFalse("shouldnt be euqal", a.isEqual(b));
    }

    @Test
    public void testLocation(){
        Location loc = new Location();
        assertTrue("default location should be none", loc.getLocationName() == "None");
        assertTrue("default coords should be all -1.", loc.coordinate.isEqual(new CartesianCoordinate(-1.0,-1.0,-1.0)));

        loc.setLocationName("NEW Location");
        assertTrue("Setter not working", loc.getLocationName() == "NEW Location");

        loc.coordinate = new CartesianCoordinate(120,234,1241);
        Location loc2 = new Location();
        assertFalse("Sould be two different Locations", loc.equals(loc2));
        assertTrue("Sould be the same Location", loc.equals(loc));
        
        loc2.setLocationName("NEW Location");
        loc2.coordinate = new CartesianCoordinate(120,234,1241);
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

        PhotoFactory fac = PhotoFactory.getInstance();
        assertTrue(fac instanceof EnvironmentPhotoFactory);
        Photo ph = fac.createPhoto();
        assertTrue(ph instanceof EnvironmentPhoto);
        ph = fac.createPhoto(PhotoId.getIdFromInt(120));
        assertTrue(ph instanceof EnvironmentPhoto);

        PhotoManager man = PhotoManager.getInstance();
        assertTrue(man instanceof EnvironmentPhotoManager);

    }
}

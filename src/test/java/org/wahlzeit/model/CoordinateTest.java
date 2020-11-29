package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CoordinateTest {
    @Test
    public void testCoordinateConstructors(){
        CartesianCoordinate coordinate = new CartesianCoordinate();
        assertEquals(Double.compare(coordinate.getX(),1.0), 0 );
        assertEquals(Double.compare(coordinate.getY(),2.0), 0 );
        assertEquals(Double.compare(coordinate.getZ(),3.0), 0 );
        coordinate = new CartesianCoordinate(4.0,0.5,14.3);
        assertEquals(Double.compare(coordinate.getX(),4.0) , 0 );
        assertEquals(Double.compare(coordinate.getY(),0.5) , 0 );
        assertEquals(Double.compare(coordinate.getZ(),14.3), 0 );
 
    }

    @Test
    public void testCoordinateDistance(){
        CartesianCoordinate a = new CartesianCoordinate(1.0,0.0,0.0);
        CartesianCoordinate b = new CartesianCoordinate(0.0,0.0,0.0);
        double distance = a.distance(b);
        assertEquals(Double.compare(distance,1.0), 0 );
        b = new CartesianCoordinate(1.0,3.0,4.0);
        assertFalse("Coordinates shouldnt be close", a.isClose(b));
        b = new CartesianCoordinate(1.03,0.0,0.0);
        assertTrue("Coordinates should be close", a.isClose(b));
    }

    @Test
    public void testCoordinateEquality(){
        CartesianCoordinate a = new CartesianCoordinate(1.0,4.0,8.03434);
        CartesianCoordinate b = new CartesianCoordinate(1.0,4.0,8.03434);
        assertTrue("should be euqal", a.isEqual(b));

        a = new CartesianCoordinate(1.0402,4.0,8.03434);
        b = new CartesianCoordinate(1.0,4.0,8.03434);
        assertFalse("shouldnt be euqal", a.isEqual(b));
    }
}

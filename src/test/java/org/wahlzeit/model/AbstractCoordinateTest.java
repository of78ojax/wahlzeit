package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class AbstractCoordinateTest {

    @Test
    public void testCoordinateEquality(){
        CartesianCoordinate a = new CartesianCoordinate(1.0,4.0,8.03434);
        CartesianCoordinate b = new CartesianCoordinate(1.0,4.0,8.03434);
        assertTrue("should be euqal", a.isEqual(b));

        a = new CartesianCoordinate(1.0402,4.0,8.03434);
        b = new CartesianCoordinate(1.0,4.0,8.03434);
        assertFalse("shouldnt be euqal", a.isEqual(b));

        SphericCoordinate a1 = new SphericCoordinate(1.0,.5,0.25);
        SphericCoordinate b1 = new SphericCoordinate(1.0,.5,0.25);

        assertTrue("should be euqal", a1.isEqual(b1));

        a1 = new SphericCoordinate(1.0402,4.0,8.03434);
        b1 = new SphericCoordinate(1.0,4.0,8.03434);
        assertFalse("shouldnt be euqal", a1.isEqual(b1));
    }

    @Test
    public void testCentralAngle(){
        SphericCoordinate a = new SphericCoordinate(1.0,0.0,0.0);
        SphericCoordinate b = new SphericCoordinate(1.0,0.0,Math.PI/2.0);
        assertEquals(Math.PI/2.0, a.getCentralAngle(b),0.0001);
    }

    @Test
    public void testConversion(){
        SphericCoordinate a = new SphericCoordinate(-1.0,12.0,0.0);
        CartesianCoordinate b = a.asCartesianCoordinate();
        SphericCoordinate c = b.asSphericCoordinate();
        assertTrue(a.isEqual(c));

        CartesianCoordinate a1 = new CartesianCoordinate(1.0,0.0,0.0);
        SphericCoordinate b1 = a1.asSphericCoordinate();
        CartesianCoordinate c1 = b1.asCartesianCoordinate();
        assertTrue(a1.isEqual(c1));

    }

    @Test(expected = AssertionError.class)
    public void testSphericNaN(){
        new SphericCoordinate(Double.NaN, 0.0,0.0);
    }

    @Test(expected = AssertionError.class)
    public void testCarthesianNaN(){
        new CartesianCoordinate( 0.0,0.0,Double.NaN);
    }

    @Test(expected = AssertionError.class)
    public void testValidCoordinate(){
        SphericCoordinate a = new SphericCoordinate(-1.0,12.0,0.0);
        a.isEqual(null);
    }

}

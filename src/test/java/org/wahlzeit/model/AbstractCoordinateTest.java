package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class AbstractCoordinateTest {
    
    @Test
    public void testCoordinateEquality() {
        CartesianCoordinate a = new CartesianCoordinate(1.0, 4.0, 8.03434);
        CartesianCoordinate b = new CartesianCoordinate(1.0, 4.0, 8.03434);
        try {
            assertTrue("should be euqal", a.isEqual(b));
        } catch (CoordinateOperationException e) {
            fail();
        }

        a = new CartesianCoordinate(1.0402, 4.0, 8.03434);
        b = new CartesianCoordinate(1.0, 4.0, 8.03434);
        try {
            assertFalse("should be euqal", a.isEqual(b));
        } catch (CoordinateOperationException e) {
            fail();
        }

        SphericCoordinate a1 = new SphericCoordinate(1.0, .5, 0.25);
        SphericCoordinate b1 = new SphericCoordinate(1.0, .5, 0.25);

        try {
            assertTrue("should be euqal", a1.isEqual(b1));
        } catch (CoordinateOperationException e) {
            fail();
        }

        a1 = new SphericCoordinate(1.0402, 4.0, 8.03434);
        b1 = new SphericCoordinate(1.0, 4.0, 8.03434);
        try {
            assertFalse("should be euqal", a1.isEqual(b1));
        } catch (CoordinateOperationException e) {
            fail();
        }
    }

    @Test
    public void testCentralAngle() {
        SphericCoordinate a = new SphericCoordinate(1.0, 0.0, 0.0);
        SphericCoordinate b = new SphericCoordinate(1.0, 0.0, Math.PI / 2.0);
        try {
            assertEquals(Math.PI / 2.0, a.getCentralAngle(b), 0.0001);
        } catch (CoordinateOperationException e) {
            fail();
        }
    }

    @Test
    public void testConversion() {
        SphericCoordinate a = new SphericCoordinate(-1.0, 12.0, 0.0);
        CartesianCoordinate b = a.asCartesianCoordinate();
        SphericCoordinate c = b.asSphericCoordinate();
        try {
            assertTrue(a.isEqual(c));

        } catch (CoordinateOperationException e) {
            fail();
        }

        CartesianCoordinate a1 = new CartesianCoordinate(1.0, 0.0, 0.0);
        SphericCoordinate b1 = a1.asSphericCoordinate();
        CartesianCoordinate c1 = b1.asCartesianCoordinate();
        try {
            assertTrue(a1.isEqual(c1));
        } catch (CoordinateOperationException e) {
            fail();
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testSphericNaN() {
        new SphericCoordinate(Double.NaN, 0.0, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCarthesianNaN() {
        new CartesianCoordinate(0.0, 0.0, Double.NaN);
    }

    @Test
    public void testValidCoordinate() {
        SphericCoordinate a = new SphericCoordinate(-1.0, 12.0, 0.0);
        try {
            a.isEqual(null);
            fail();
        } catch (CoordinateOperationException e) {
            // Success
        }

    }

    @Test
    public void testHash() {
        // Method 1
        double f1 = .0;
        for (int i = 1; i <= 11; i++) {
            f1 += .1;
        }
        f1 = 2.2 - f1;

        // Method 2
        double f2 = .1 * 11;
        SphericCoordinate a = new SphericCoordinate(f1, f1, f1);
        SphericCoordinate b = new SphericCoordinate(f2, f2, f2);

        assertEquals(a.hashCode(), b.hashCode());

    }

}

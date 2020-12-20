package org.wahlzeit.model;

import java.sql.*;
import java.util.Objects;

import org.apache.log4j.spi.ThrowableInformation;

public abstract class AbstractCoordinate implements Coordinate {

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assert rset != null;

        double sqlx = rset.getDouble("loc_x");
        double sqly = rset.getDouble("loc_y");
        double sqlz = rset.getDouble("loc_z");
        setFirstElement(sqlx);
        setSecondElement(sqly);
        setThirdElement(sqlz);

        assertResultEquals(sqlx, getFirstElement());
        assertResultEquals(sqly, getSecondElement());
        assertResultEquals(sqlz, getThirdElement());
        assertClassInvariants();
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assert rset != null;

        rset.updateDouble("loc_x", getFirstElement());
        rset.updateDouble("loc_y", getSecondElement());
        rset.updateDouble("loc_z", getThirdElement());
        rset.updateString("coord_type", getType());

        assertResultEquals(rset.getDouble("loc_x"), getFirstElement());
        assertResultEquals(rset.getDouble("loc_y"), getSecondElement());
        assertResultEquals(rset.getDouble("loc_z"), getThirdElement());
        assert rset.getString("coord_type") == getType();
        assertClassInvariants();
    }

    @Override
    public double getCartesianDistance(Coordinate other) throws CoordinateOperationException{
        double distance;
        try{
        assertClassInvariants();
        assertValidCoordinate(other);

        CartesianCoordinate me = asCartesianCoordinate();
        CartesianCoordinate oCoord = other.asCartesianCoordinate();
        distance = me.getDistance(oCoord);

        assertNotNan(distance);
        assertClassInvariants();
        }catch(Exception e){
            throw new CoordinateOperationException("getCartesianDistance failed");
        }
        return distance;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof AbstractCoordinate)) {
            return false;
        }
        AbstractCoordinate other = (AbstractCoordinate) obj;
        boolean res;
        try {
            res = this.isEqual(other);
        } catch (Exception e) {
            return false;
        }
        return res;
    }

    @Override
    public int hashCode() {
        int hash;
        try {
            assertClassInvariants();
            CartesianCoordinate coord = this.asCartesianCoordinate();
            hash = Objects.hash(coord.getX(), coord.getY(), coord.getZ());
            assertClassInvariants();
        } catch (Exception e) {
            hash = -1;
        }
        return hash;
    }

    @Override
    public boolean isEqual(Coordinate other) throws CoordinateOperationException {
        boolean result;
        try {
            assertClassInvariants();
            assertValidCoordinate(other);

            CartesianCoordinate me = this.asCartesianCoordinate();
            CartesianCoordinate o = other.asCartesianCoordinate();
            result = me.doIsEqual(o);

            assertClassInvariants();
        } catch (Exception e) {
            throw new CoordinateOperationException("isEqual failed");
        }
        return result;

    }

    @Override
    public double getCentralAngle(Coordinate other) throws CoordinateOperationException {
        double result;
        try {

            assertClassInvariants();
            assertValidCoordinate(other);

            SphericCoordinate me = this.asSphericCoordinate();
            SphericCoordinate oCoordinate = other.asSphericCoordinate();
            result = me.doGetCentralAngle(oCoordinate);

            assert result >= 0.0;

            assertClassInvariants();
        } catch (Exception e) {
            throw new CoordinateOperationException("centralAngle could not be computed");
        }
        return result;

    }

    protected static final double MAX_DIFF = 0.1;

    protected boolean isClose(AbstractCoordinate other) throws CoordinateOperationException {

        boolean result;
        try {
            assertClassInvariants();
            assertValidCoordinate(other);

            CartesianCoordinate me = this.asCartesianCoordinate();
            CartesianCoordinate oCoordinate = other.asCartesianCoordinate();
            double distance = me.getDistance(oCoordinate);

            assertBoundaries(distance, 0.0, Double.MAX_VALUE);

            result = distance < MAX_DIFF;

            assertClassInvariants();
        } catch (Exception e) {
            throw new CoordinateOperationException("isClose failed");
        }
        return result;
    }

    protected void assertNotNan(double d) throws IllegalArgumentException {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException("Value can't be NaN");
        }
    }

    // inclusive boundary check
    protected void assertBoundaries(double result, double lower, double upper) throws IllegalBoundaryException {
        if (result > lower && result < upper)
            return;
        if (Math.abs(result - lower) < 0.000001)
            return;
        if (Math.abs(result - upper) < 0.000001)
            return;

        throw new IllegalBoundaryException("Result is not between boundaries");
    }

    protected void assertResultEquals(double res, double should) throws IllegalResultException {
        if (!(Math.abs(res - should) < 0.000001))
            throw new IllegalResultException("Result differs too much from what is Expected");
    }

    protected void assertValidCoordinate(Coordinate other) throws IllegalArgumentException, IllegalStateException {
        if (other == null)
            throw new IllegalArgumentException("Parameter was null");
        //
        assert other instanceof AbstractCoordinate;
        AbstractCoordinate obj = (AbstractCoordinate) other;
        obj.assertClassInvariants();
    }

    protected void assertClassInvariants() throws IllegalStateException {
        try {
            assertNotNan(getFirstElement());
            assertNotNan(getSecondElement());
            assertNotNan(getThirdElement());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Object has Invalid State");
        }

    }

    protected abstract AbstractCoordinate convertToSameType(Coordinate other)  throws CoordinateOperationException;

    protected abstract double getFirstElement();

    protected abstract double getSecondElement();

    protected abstract double getThirdElement();

    protected abstract void setFirstElement(double value);

    protected abstract void setSecondElement(double value);

    protected abstract void setThirdElement(double value);

}

package org.wahlzeit.model;

import java.sql.*;
import java.util.Objects;

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
    public double getCartesianDistance(Coordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);

        CartesianCoordinate me = asCartesianCoordinate();
        CartesianCoordinate oCoord = other.asCartesianCoordinate();
        double distance = me.getDistance(oCoord);

        assertNotNan(distance);
        assertClassInvariants();
        return distance;

    }

    @Override
    public boolean equals(Object obj) {
        assert obj != null;
        if (obj == this)
            return true;
        if (!(obj instanceof AbstractCoordinate)) {
            return false;
        }
        AbstractCoordinate other = (AbstractCoordinate) obj;
        return this.isEqual(other);
    }

    @Override
    public int hashCode() {
        assertClassInvariants();

        CartesianCoordinate coord = this.asCartesianCoordinate();
        int hash = Objects.hash(coord.getX(), coord.getY(), coord.getZ());

        assertClassInvariants();
        return hash;
    }

    @Override
    public boolean isEqual(Coordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);

        CartesianCoordinate me = this.asCartesianCoordinate();
        CartesianCoordinate o = other.asCartesianCoordinate();
        boolean result = me.doIsEqual(o);

        assertClassInvariants();
        return result;

    }

    @Override
    public double getCentralAngle(Coordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);

        SphericCoordinate me = this.asSphericCoordinate();
        SphericCoordinate oCoordinate = other.asSphericCoordinate();
        double result = me.doGetCentralAngle(oCoordinate);

        assertNotNan(result);
        assertClassInvariants();
        return result;

    }

    protected static final double MAX_DIFF = 0.1;

    protected boolean isClose(AbstractCoordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);

        CartesianCoordinate me = this.asCartesianCoordinate();
        CartesianCoordinate oCoordinate = other.asCartesianCoordinate();
        double distance = me.getDistance(oCoordinate);

        assert distance >= 0.0;
        assertNotNan(distance);

        boolean result = distance < MAX_DIFF;

        assertClassInvariants();
        return result;
    }

    protected void assertNotNan(double d){
        assert !Double.isNaN(d);
    }

    protected void assertResultEquals(double res, double should) {
        assert Math.abs(res - should) < 0.000001;
    }

    protected void assertValidCoordinate(Coordinate other) {
        assert other != null;
        assert other instanceof AbstractCoordinate;
        AbstractCoordinate obj = (AbstractCoordinate) other;
        obj.assertClassInvariants();
    }

    protected void assertClassInvariants() {
        assertNotNan(getFirstElement());
        assertNotNan(getSecondElement());
        assertNotNan(getThirdElement());
    }

    protected abstract AbstractCoordinate convertToSameType(Coordinate other);

    protected abstract double getFirstElement();

    protected abstract double getSecondElement();

    protected abstract double getThirdElement();

    protected abstract void setFirstElement(double value);

    protected abstract void setSecondElement(double value);

    protected abstract void setThirdElement(double value);

}

package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SphericCoordinate extends AbstractCoordinate {
    private final CoordinateData data;

    SphericCoordinate(double r, double phi, double theta) {
        assertNotNan(r);
        assertNotNan(phi);
        assertNotNan(theta);

        double nr = Math.abs(r);
        double nphi = phi % (Math.PI / 2);
        double ntheta = Math.abs(theta) % (Math.PI);

        double x = nr * Math.sin(ntheta) * Math.cos(nphi);
        double y = nr * Math.sin(ntheta) * Math.sin(nphi);
        double z = nr * Math.cos(ntheta);

        data = CoordinateData.getCoordinateData(x, y, z);

        assertClassInvariants();

    }

    SphericCoordinate(CartesianCoordinate coord) {
        assertValidCoordinate(coord);

        data = coord.getData();

        assertClassInvariants();
    }

    SphericCoordinate(ResultSet rset) throws SQLException {
        assert rset != null;

        double sqlx = rset.getDouble("loc_x");
        double sqly = rset.getDouble("loc_y");
        double sqlz = rset.getDouble("loc_z");

        double nr = Math.abs(sqlx);
        double nphi = sqly % (Math.PI / 2);
        double ntheta = Math.abs(sqlz) % (Math.PI);

        double x = nr * Math.sin(ntheta) * Math.cos(nphi);
        double y = nr * Math.sin(ntheta) * Math.sin(nphi);
        double z = nr * Math.cos(ntheta);

        data = CoordinateData.getCoordinateData(x, y, z);

        assertClassInvariants();
    }

    @Override
    public Coordinate readFrom(ResultSet rset) throws SQLException {
        return new SphericCoordinate(rset);
    }

    @Override
    protected void assertClassInvariants() throws IllegalStateException {
        super.assertClassInvariants();
        try {
            assertBoundaries(getRadius(), 0.0, Double.MAX_VALUE);
            assertBoundaries(getTheta(), 0, Math.PI);
            assertBoundaries(getPhi(), -Math.PI / 2, Math.PI / 2);
        } catch (Exception e) {
            throw new IllegalStateException("Object is in a invalid state");
        }

    }

    @Override
    protected CoordinateData getData() {
        return data;
    }

    @Override
    protected double getFirstElement() {
        return getRadius();
    }

    @Override
    protected double getSecondElement() {
        return getPhi();
    }

    @Override
    protected double getThirdElement() {
        return getTheta();
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        CartesianCoordinate result = new CartesianCoordinate(this);

        assertValidCoordinate(result);
        assertClassInvariants();
        return result;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    protected AbstractCoordinate convertToSameType(Coordinate other) throws CoordinateOperationException {
        assertClassInvariants();

        AbstractCoordinate obj = other.asSphericCoordinate();

        assertValidCoordinate(obj);
        assertClassInvariants();
        return obj;
    }

    public double doGetCentralAngle(SphericCoordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);
        double long1 = getPhi();
        double lat1 = getTheta();
        double long2 = other.getPhi();
        double lat2 = other.getTheta();
        double result = Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(Math.abs(long1 - long2)));

        assertNotNan(result);
        assertBoundaries(result, 0.0, 2 * Math.PI);
        assertClassInvariants();
        return result;
    }

    @Override
    public String getType() {
        return Coordinate.SPHERICAL;
    }

    protected double getRadius() {
        double x = data.getX();
        double y = data.getY();
        double z = data.getZ();

        double r = Math.sqrt(x + y + z);
        return r;
    }

    protected double getPhi() {
        double x = data.getX();
        double y = data.getY();
        if (x == 0.)
            return 0.0;

        return Math.atan(y / x);
    }

    protected double getTheta() {
        double z = data.getZ();
        double r = getRadius();
        if (r == 0.)
            return 0.;
        return Math.acos(z / r);
    }

}

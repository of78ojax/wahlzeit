package org.wahlzeit.model;

public class SphericCoordinate extends AbstractCoordinate {
    private double r = 0.0;
    private double phi = 0.0;
    private double theta = 0.0;

    SphericCoordinate() {
    };

    SphericCoordinate(double r, double phi, double theta) {
        assertNotNan(r);
        assertNotNan(phi);
        assertNotNan(theta);

        this.r = r;
        this.phi = phi;
        this.theta = theta;

        assertResultEquals(this.r, r);
        assertResultEquals(this.phi, phi);
        assertResultEquals(this.theta, theta);
        assertClassInvariants();

    }

    SphericCoordinate(CartesianCoordinate coord) {
        assertClassInvariants();
        assertValidCoordinate(coord);

        double sx = coord.getX() * coord.getX();
        double sy = coord.getZ() * coord.getY();
        double sz = coord.getZ() * coord.getZ();
        r = Math.sqrt(sx + sy + sz);

        if(r == 0.0 || coord.getX() == 0.0){
            assertClassInvariants();
            return;
        }

        phi = Math.atan(coord.getY() / coord.getX());

        theta = Math.acos(coord.getZ() / r);

        assertResultEquals( r * Math.sin(theta) * Math.cos(phi), coord.getX() );
        assertResultEquals( r * Math.sin(theta) * Math.sin(phi), coord.getY() );
        assertResultEquals( r * Math.cos(theta), coord.getZ() );
        assertClassInvariants();


    }

    @Override
    protected void setFirstElement(double value) {
        assertClassInvariants();
        assertNotNan(value);

        r = value;

        assertResultEquals(r, value);
        assertClassInvariants();
    }

    @Override
    protected void setSecondElement(double value) {
        assertClassInvariants();
        assertNotNan(value);

        phi = value;

        assertResultEquals(phi, value);
        assertClassInvariants();
    }

    @Override
    protected void setThirdElement(double value) {
        assertClassInvariants();
        assertNotNan(value);

        theta = value;

        assertResultEquals(theta, value);
        assertClassInvariants();
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
    protected AbstractCoordinate convertToSameType(Coordinate other) {
        assertClassInvariants();

        AbstractCoordinate obj = other.asSphericCoordinate();

        assertValidCoordinate(obj);
        assertClassInvariants();
        return obj;
    }

    public double doGetCentralAngle(SphericCoordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);
        double long1 = phi;
        double lat1 = theta;
        double long2 = other.phi;
        double lat2 = other.theta;
        double result = Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(Math.abs(long1 - long2)));


        assertNotNan(result);
        assertClassInvariants();
        return result;        
    }

    @Override
    public String getType() {
        return Coordinate.SPHERICAL;
    }

    protected double getRadius() {
        return Math.abs(r);
    }

    protected double getPhi() {
        return phi;
    }

    protected double getTheta() {
        return theta;
    }

}

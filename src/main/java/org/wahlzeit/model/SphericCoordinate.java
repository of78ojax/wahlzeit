package org.wahlzeit.model;

public class SphericCoordinate extends AbstractCoordinate {
    private double r = 0.0;
    private double phi = 0.0;
    private double theta = 0.0;

    SphericCoordinate() {
    };

    SphericCoordinate(double r, double phi, double theta) {
        this.r = r;
        this.phi = phi;
        this.theta = theta;
    }

    SphericCoordinate(CartesianCoordinate coord) {
        double sx = coord.getX() * coord.getX();
        double sy = coord.getZ() * coord.getY();
        double sz = coord.getZ() * coord.getZ();
        r = Math.sqrt(sx + sy + sz);

        if(r == 0.0 || coord.getX() == 0.0)
            return;

        phi = Math.atan(coord.getY() / coord.getX());

        theta = Math.acos(coord.getZ() / r);
    }

    @Override
    protected void setFirstElement(double value) {
        r = value;
    }

    @Override
    protected void setSecondElement(double value) {
        phi = value;
    }

    @Override
    protected void setThirdElement(double value) {
        theta = value;
    }

    @Override
    protected double getFirstElement() {
        return r;
    }

    @Override
    protected double getSecondElement() {
        return phi;
    }

    @Override
    protected double getThirdElement() {
        return theta;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(this);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }


    @Override
    protected AbstractCoordinate convertToSameType(Coordinate other) {
        return other.asSphericCoordinate();
    }

    public double doGetCentralAngle(SphericCoordinate other) {
        double long1 = phi;
        double lat1 = theta;
        double long2 = other.phi;
        double lat2 = other.theta;
        return Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(Math.abs(long1 - long2)));
    }

    @Override
    public String getType() {
        return Coordinate.SPHERICAL;
    }

    protected double getRadius() {
        return r;
    }

    protected double getPhi() {
        return phi;
    }

    protected double getTheta() {
        return theta;
    }

}

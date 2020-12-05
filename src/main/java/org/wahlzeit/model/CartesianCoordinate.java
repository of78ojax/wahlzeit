package org.wahlzeit.model;


public class CartesianCoordinate extends AbstractCoordinate {
    private double x = 1.0;
    private double y = 2.0;
    private double z = 3.0;

    

    protected CartesianCoordinate() {
    }

    protected CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected CartesianCoordinate(SphericCoordinate coord) {
        double r = coord.getRadius();
        double theta = coord.getTheta();
        double phi = coord.getPhi();

        this.x = r * Math.sin(theta) * Math.cos(phi);
        this.y = r * Math.sin(theta) * Math.sin(phi);
        this.z = r * Math.cos(theta);
    }

    protected void setCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }

    protected double getZ() {
        return z;
    }

    @Override
    protected double getFirstElement() {
        return getX();
    }

    @Override
    protected double getSecondElement() {
        return getY();
    }
    @Override
    protected double getThirdElement() {
        return getZ();
    }

    @Override
    protected void setFirstElement(double value) {
        x = value;
    }

    @Override
    protected void setSecondElement(double value) {
        y = value;
    }

    @Override
    protected void setThirdElement(double value) {
        z = value;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(this);
    }

    @Override
    protected AbstractCoordinate convertToSameType(Coordinate other) {
        return other.asCartesianCoordinate();
    }

    @Override
    public String getType(){
        return Coordinate.CARTESIAN;
    }

    protected double getDistance(CartesianCoordinate other) {
        return (new CartesianCoordinate(this.x - other.x, this.y - other.y, this.z - other.z)).length();
    }

    private double length() {
        double xSquare = x * x;
        double ySquare = y * y;
        double zSquare = z * z;
        return Math.sqrt(xSquare + ySquare + zSquare);
    }

}

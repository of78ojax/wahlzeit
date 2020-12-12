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
        assertClassInvariants();
        assertValidCoordinate(coord);

        double r = coord.getRadius();
        double theta = coord.getTheta();
        double phi = coord.getPhi();

        this.x = r * Math.sin(theta) * Math.cos(phi);
        this.y = r * Math.sin(theta) * Math.sin(phi);
        this.z = r * Math.cos(theta);

        assertResultEquals(Math.sqrt((x*x) + (y*y) + (z*z)), r);
        if(x != 0.0 && r != 0.0){
            assertResultEquals(Math.atan(y/x), phi % Math.PI);
            assertResultEquals(Math.acos(z/r), theta % Math.PI);
        }
        assertClassInvariants();

    }

    protected void setCoordinates(double x, double y, double z) {
        assertClassInvariants();
        assertNotNan(x);
        assertNotNan(y);
        assertNotNan(z);

        this.x = x;
        this.y = y;
        this.z = z;

        assertResultEquals(this.x, x);
        assertResultEquals(this.y, y);
        assertResultEquals(this.z, z);
        assertClassInvariants();
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
        assertClassInvariants();
        assertNotNan(value);

        x = value;

        assertResultEquals(x, value);
        assertClassInvariants();
    }

    @Override
    protected void setSecondElement(double value) {
        assertClassInvariants();
        assertNotNan(value);

        y = value;

        assertResultEquals(y, value);
        assertClassInvariants();
    }

    @Override
    protected void setThirdElement(double value) {
        assertClassInvariants();
        assertNotNan(value);

        z = value;

        assertResultEquals(z, value);
        assertClassInvariants();
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        
        SphericCoordinate result = new SphericCoordinate(this);
        
        assertValidCoordinate(result);
        assertClassInvariants();
        return result;
    }

    @Override
    protected AbstractCoordinate convertToSameType(Coordinate other) {
        assertClassInvariants();

        AbstractCoordinate obj = other.asCartesianCoordinate();

        assertValidCoordinate(obj);
        assertClassInvariants();
        return obj;
    }

    @Override
    public String getType(){
        return Coordinate.CARTESIAN;
    }

    protected boolean doIsEqual(CartesianCoordinate other){
        assertClassInvariants();
        assertValidCoordinate(other);

        double eps = 0.00001;
        double isR = Math.abs(this.getFirstElement() - other.getFirstElement());
        double isP = Math.abs(this.getSecondElement() - other.getSecondElement());
        double isT = Math.abs(this.getThirdElement() - other.getThirdElement());
        boolean result = (isR < eps && isP < eps && isT < eps);

        assertClassInvariants();
        return result;
    }

    protected double getDistance(CartesianCoordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);
        double distance = (new CartesianCoordinate(this.x - other.x, this.y - other.y, this.z - other.z)).length();
        
        assertNotNan(distance);
        assertClassInvariants();
        return distance;
    }

    private double length() {
        assertClassInvariants();

        double xSquare = x * x;
        double ySquare = y * y;
        double zSquare = z * z;
        double distance = Math.sqrt(xSquare + ySquare + zSquare);

        assertNotNan(distance);
        assertClassInvariants();

        return distance;
    }

}

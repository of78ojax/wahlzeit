package org.wahlzeit.model;
import java.sql.*;

import java.lang.Double;

public class CartesianCoordinate implements Coordinate {
    private double x = 1.0;
    private double y = 2.0;
    private double z = 3.0;

    protected static final double MAX_DIFF = 0.1;

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

        this.x = r * Math.sin(theta) + Math.cos(phi);
        this.y = r * Math.sin(theta) + Math.sin(phi);
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
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(this);
    }

    @Override
    public double getCartesianDistance(Coordinate other) {
            return distance(other.asCartesianCoordinate());
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        SphericCoordinate me = this.asSphericCoordinate();
        return me.getCentralAngle(other);
        
    }

    @Override
    public boolean isEqual(Coordinate other) {
        CartesianCoordinate comp = other.asCartesianCoordinate();
        int isX = Double.compare(this.x, comp.x);
        int isY = Double.compare(this.y, comp.y);
        int isZ = Double.compare(this.z, comp.z);
        return (isX == 0 && isY == 0 && isZ == 0);
    }

    @Override
    public String getType(){
        return Coordinate.CARTESIAN;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException{
        x = rset.getDouble("loc_x");
        y = rset.getDouble("loc_y");
        z = rset.getDouble("loc_z");

    }
    @Override
    public void writeOn(ResultSet rset) throws SQLException{
        rset.updateDouble("loc_x",x);
        rset.updateDouble("loc_y",y);
        rset.updateDouble("loc_z",z);
        rset.updateString("coord_type",Coordinate.CARTESIAN);
    }

    protected boolean isClose(CartesianCoordinate other) {
        return this.distance(other) < MAX_DIFF;
    }

    protected double distance(CartesianCoordinate other) {
        return (new CartesianCoordinate(this.x - other.x, this.y - other.y, this.z - other.z)).length();
    }

    private double length() {
        double xSquare = x * x;
        double ySquare = y * y;
        double zSquare = z * z;
        return Math.sqrt(xSquare + ySquare + zSquare);
    }

}

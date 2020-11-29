package org.wahlzeit.model;

import java.sql.*;
import java.sql.ResultSet;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

public class SphericCoordinate implements Coordinate {
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

        phi = Math.atan(coord.getY() / coord.getX());

        theta = Math.acos(coord.getZ() / r);
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
    public boolean isEqual(Coordinate other) {
        SphericCoordinate comp = other.asSphericCoordinate();
        int isR = Double.compare(this.r, comp.r);
        int isP = Double.compare(this.phi, comp.phi);
        int isT = Double.compare(this.theta, comp.theta);
        return (isR == 0 && isP == 0 && isT == 0);
    }

    @Override
    public double getCartesianDistance(Coordinate other) {
        CartesianCoordinate me = this.asCartesianCoordinate();
        CartesianCoordinate comp = other.asCartesianCoordinate();
        return me.distance(comp);
    }

    @Override
    public double getCentralAngle(Coordinate other) {
        SphericCoordinate comp = other.asSphericCoordinate();
        double long1 = phi;
        double lat1 = theta;
        double long2 = comp.phi;
        double lat2 = comp.theta;
        return Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(Math.abs(long1 - long2)));
    }

    @Override
    public String getType() {
        return Coordinate.SPHERICAL;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        r = rset.getDouble("loc_x");
        phi = rset.getDouble("loc_y");
        theta = rset.getDouble("loc_z");

    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("loc_x", r);
        rset.updateDouble("loc_y", phi);
        rset.updateDouble("loc_z", theta);
        rset.updateString("coord_type", Coordinate.SPHERICAL);
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

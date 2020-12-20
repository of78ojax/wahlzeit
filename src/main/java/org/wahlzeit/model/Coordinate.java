package org.wahlzeit.model;

import java.sql.*;

public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate() throws CoordinateOperationException;

    double getCartesianDistance(Coordinate other) throws CoordinateOperationException;

    SphericCoordinate asSphericCoordinate() throws CoordinateOperationException;

    double getCentralAngle(Coordinate other) throws CoordinateOperationException;

    boolean isEqual(Coordinate other) throws CoordinateOperationException;

    public final String SPHERICAL = "Spherical";
    public final String CARTESIAN = "Cartesian";

    void readFrom(ResultSet rset) throws SQLException;

    void writeOn(ResultSet rset) throws SQLException;

    String getType();

}

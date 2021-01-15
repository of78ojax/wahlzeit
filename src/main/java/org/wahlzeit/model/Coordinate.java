package org.wahlzeit.model;
import java.sql.*;


@PatternInstance(
    patternName = "Bridge",
    participants = {"Coordinate", "AbstractCoordinate", "SphericCoordinate", "CarthesianCoordinate"}
)
public interface Coordinate {

   
    CartesianCoordinate asCartesianCoordinate() throws CoordinateOperationException;

    double getCartesianDistance(Coordinate other) throws CoordinateOperationException;

    SphericCoordinate asSphericCoordinate() throws CoordinateOperationException;

    double getCentralAngle(Coordinate other) throws CoordinateOperationException;

    boolean isEqual(Coordinate other) throws CoordinateOperationException;

    public final String SPHERICAL = "Spherical";
    public final String CARTESIAN = "Cartesian";

    Coordinate readFrom(ResultSet rset) throws SQLException;

    void writeOn(ResultSet rset) throws SQLException;

    String getType();

}

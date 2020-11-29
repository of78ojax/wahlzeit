package org.wahlzeit.model;
import java.sql.*;

public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();
    double getCartesianDistance(Coordinate other);
    SphericCoordinate asSphericCoordinate();
    double getCentralAngle(Coordinate other);
    boolean isEqual(Coordinate other);

    public final String SPHERICAL = "Spherical";
    public final String CARTESIAN = "Cartesian";
    void readFrom(ResultSet rset) throws SQLException;
    void writeOn(ResultSet rset) throws SQLException;
    String getType();

}

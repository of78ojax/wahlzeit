package org.wahlzeit.model;

import java.sql.*;

import java.util.Objects;

public class Location {
    private String name = "None";
    protected Coordinate coordinate = new CartesianCoordinate(-1.0, -1.0, -1.0);

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Location)) {
            return false;
        }
        Location other = (Location) o;
        boolean result = this.name == other.name;
        try {
            result &= coordinate.isEqual(other.coordinate);
        } catch (CoordinateOperationException e) {
            return false;
        }
        return result;
    }

    @Override
    public int hashCode() {
        try {
            CartesianCoordinate coord = coordinate.asCartesianCoordinate();
            return Objects.hash(name, coord.getX(), coord.getY(), coord.getZ());
            
        } catch (Exception e) {
            return 0;
        }
        
    }

    protected boolean isClose(Location other) {
        try {
            CartesianCoordinate coord = coordinate.asCartesianCoordinate();
            CartesianCoordinate ocoord = other.coordinate.asCartesianCoordinate();
            return coord.isClose(ocoord);
        } catch (CoordinateOperationException e) {
            return false;
        }

    }

    protected String getLocationName() {
        return name;
    }

    protected void setLocationName(String name) {
        this.name = name;
    }

    public void readFrom(ResultSet rset) throws SQLException {
        String sqlName;
        String sqlType;
        
        try {
            sqlName = rset.getString("location_name");
            sqlType = rset.getString("coord_type");

        } catch (SQLException e) {
            sqlName = "Not-found";
            sqlType = "Not-found";
        }

        name = sqlName;
        if (sqlType == Coordinate.SPHERICAL) {
            coordinate = new SphericCoordinate(rset);
        } else {
            coordinate = new CartesianCoordinate(rset);
        }

    }

    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateString("location_name", name);
        coordinate.writeOn(rset);
    }

}

package org.wahlzeit.model;
import java.sql.*;

import java.util.Objects;

public class Location {
    private String name = "None";
    protected Coordinate coordinate = new CartesianCoordinate(-1.0,-1.0,-1.0);


    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Location)) {
            return false;
        }
       Location other = (Location) o;
        return coordinate.isEqual(other.coordinate) && this.name == other.name;
    }

    @Override
    public int hashCode() {
        CartesianCoordinate coord = coordinate.asCartesianCoordinate();
        return Objects.hash(name, coord.getX(), coord.getY(),coord.getZ());
    }

    protected boolean isClose(Location other){
        CartesianCoordinate coord = coordinate.asCartesianCoordinate();
        CartesianCoordinate ocoord = other.coordinate.asCartesianCoordinate();
        return coord.isClose(ocoord);
    }

    protected String getLocationName(){
        return name;
    }

    protected void setLocationName(String name){
        this.name = name;
    }
    public void readFrom(ResultSet rset) throws SQLException {
        String txt = rset.getString("location_name");
        name = txt;
        String coordType = rset.getString("coord_type");
        if(coordType == Coordinate.SPHERICAL){
            coordinate = new SphericCoordinate();
            coordinate.readFrom(rset);
        }else{
            coordinate = new CartesianCoordinate();
            coordinate.readFrom(rset);
        }

    }

    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateString("location_name", name);
        coordinate.writeOn(rset);
    }

}

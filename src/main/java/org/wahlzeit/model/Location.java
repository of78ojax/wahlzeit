package org.wahlzeit.model;

import java.util.Objects;

public class Location {
    private String name = "None";
    protected Coordinate coordinate = new Coordinate(-1.0,-1.0,-1.0);


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
        return Objects.hash(name, coordinate.getX(), coordinate.getY(),coordinate.getZ());
    }

    protected boolean isClose(Location other){
        return this.coordinate.isClose(other.coordinate);
    }

    protected String getLocationName(){
        return name;
    }

    protected void setLocationName(String name){
        this.name = name;
    }

}

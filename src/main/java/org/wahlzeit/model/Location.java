package org.wahlzeit.model;

public class Location {
    private String name = "default";
    private Coordinate coordinate = new Coordinate();


    protected boolean equals(Location other){
        return coordinate.isEqual(other.coordinate);
    }

    protected boolean isClose(Location other){
        return this.coordinate.isNearEnough(other.coordinate);
    }

    protected String getLocationName(){
        return name;
    }

    protected void setLocationName(String name){
        this.name = name;
    }

    protected double[] getCoords(){
        return coordinate.getCoords();
    } 

    protected void setCoords(double x,double y, double z){
        coordinate.setCoordinates(x, y, z);
    }
}

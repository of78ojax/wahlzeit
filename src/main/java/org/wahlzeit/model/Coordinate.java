package org.wahlzeit.model;
import java.lang.Double;



public class Coordinate {
    private double x = 1.0;
    private double y = 2.0;
    private double z = 3.0;

    protected static final double MAX_DIFF = 0.1;


    protected Coordinate(){}

    protected Coordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected void setCoordinates(double x, double y , double z){
        this.x = x;        
        this.y = y;
        this.z = z;    
    }

    protected double getX(){
        return x;
    }
    protected double getY(){
        return y;
    }
    protected double getZ(){
        return z;
    }

    protected boolean isEqual(Coordinate other){
        int isX = Double.compare(this.x , other.x);
        int isY = Double.compare(this.y , other.y);   
        int isZ = Double.compare(this.z , other.z);      
        return (isX == 0 && isY == 0 && isZ == 0);
    }

    protected boolean isClose(Coordinate other){
        return this.distance(other) < MAX_DIFF;
    }

    protected double distance(Coordinate other){
        return (new Coordinate(this.x - other.x, this.y -other.y,this.z -other.z)).length();
    }

    private double length(){
        double xSquare = x*x;
        double ySquare = y*y;
        double zSquare = z*z;        
        return Math.sqrt(xSquare+ySquare+zSquare);
    }

}

package org.wahlzeit.model;
import java.sql.ResultSet;
import java.sql.SQLException;

final public class CartesianCoordinate extends AbstractCoordinate {
    private final CoordinateData data;

    protected CartesianCoordinate(double x, double y, double z) {
        assertNotNan(x);
        assertNotNan(y);
        assertNotNan(z);
        data = CoordinateData.getCoordinateData(x, y, z);
        assertClassInvariants();
    }

    protected CartesianCoordinate(SphericCoordinate coord) {
        assertValidCoordinate(coord);

        data = coord.getData();

        assertClassInvariants();

    }

    CartesianCoordinate(ResultSet rset) throws SQLException{
        assert rset != null;

        double x = rset.getDouble("loc_x");
        double y = rset.getDouble("loc_y");
        double z = rset.getDouble("loc_z");
        
        data = CoordinateData.getCoordinateData(x, y, z);

        assertClassInvariants();
    }

    @Override
    public Coordinate readFrom(ResultSet rset) throws SQLException {
        return new CartesianCoordinate(rset);
    }

    @Override
    protected CoordinateData getData() {
        return data;
    }

    protected double getX() {
        return data.getX();
    }

    protected double getY() {
        return data.getY();
    }

    protected double getZ() {
        return data.getZ();
    }

    @Override
    protected double getFirstElement() {
        return getX();
    }

    @Override
    protected double getSecondElement() {
        return getY();
    }
    @Override
    protected double getThirdElement() {
        return getZ();
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        
        SphericCoordinate result = new SphericCoordinate(this);
        
        assertValidCoordinate(result);
        assertClassInvariants();
        return result;
    }

    @Override
    protected AbstractCoordinate convertToSameType(Coordinate other) throws CoordinateOperationException{
        assertClassInvariants();

        AbstractCoordinate obj = other.asCartesianCoordinate();

        assertValidCoordinate(obj);
        assertClassInvariants();
        return obj;
    }

    @Override
    public String getType(){
        return Coordinate.CARTESIAN;
    }

    protected double getDistance(CartesianCoordinate other) {
        assertClassInvariants();
        assertValidCoordinate(other);
        double x = data.getX();
        double y = data.getY();
        double z = data.getZ();
        double distance = (new CartesianCoordinate(x - other.getX(), y - other.getY(), z - other.getZ())).length();
        
        assertNotNan(distance);
        assertClassInvariants();
        return distance;
    }

    private double length() {
        assertClassInvariants();
        double x = data.getX();
        double y = data.getY();
        double z = data.getZ();


        double xSquare = x * x;
        double ySquare = y * y;
        double zSquare = z * z;
        double distance = Math.sqrt(xSquare + ySquare + zSquare);

        assertNotNan(distance);
        assertClassInvariants();

        return distance;
    }

}

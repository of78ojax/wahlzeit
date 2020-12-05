package org.wahlzeit.model;
import java.sql.*;
public abstract class AbstractCoordinate implements Coordinate{

    @Override
    public void readFrom(ResultSet rset) throws SQLException{
        setFirstElement(rset.getDouble("loc_x"));
        setSecondElement(rset.getDouble("loc_y"));
        setThirdElement(rset.getDouble("loc_z"));

    }
    @Override
    public void writeOn(ResultSet rset) throws SQLException{
        rset.updateDouble("loc_x",getFirstElement());
        rset.updateDouble("loc_y",getSecondElement());
        rset.updateDouble("loc_z",getThirdElement());
        rset.updateString("coord_type",getType());
    }
    public double getCartesianDistance(Coordinate other) {
        CartesianCoordinate me = asCartesianCoordinate();
        CartesianCoordinate oCoord = other.asCartesianCoordinate();
        return me.getDistance(oCoord);

    }
    @Override
    public boolean isEqual(Coordinate other) {
        double eps = 0.00001;
        AbstractCoordinate comp = convertToSameType(other);
        double isR = Math.abs(this.getFirstElement() - comp.getFirstElement());
        double isP = Math.abs(this.getSecondElement() - comp.getSecondElement());
        double isT = Math.abs(this.getThirdElement() - comp.getThirdElement());
        return (isR < eps && isP < eps && isT < eps);
    }
    @Override
    public double getCentralAngle(Coordinate other) {
        SphericCoordinate me = this.asSphericCoordinate();
        SphericCoordinate oCoordinate = other.asSphericCoordinate();
        return me.doGetCentralAngle(oCoordinate);
        
    }

    protected static final double MAX_DIFF = 0.1;
    protected boolean isClose(AbstractCoordinate other) {
        CartesianCoordinate me = this.asCartesianCoordinate();
        CartesianCoordinate oCoordinate = other.asCartesianCoordinate();
        return me.getDistance(oCoordinate) < MAX_DIFF;
    }


    protected abstract AbstractCoordinate convertToSameType(Coordinate other);
    protected abstract double getFirstElement();
    protected abstract double getSecondElement();
    protected abstract double getThirdElement();
    protected abstract void setFirstElement(double value);
    protected abstract void setSecondElement(double value);
    protected abstract void setThirdElement(double value);
    
}

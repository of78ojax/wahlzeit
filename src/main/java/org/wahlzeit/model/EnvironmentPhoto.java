package org.wahlzeit.model;
import java.sql.*;

public class EnvironmentPhoto extends Photo {

	protected Environment environment = Environment.DEFAULT;
	
	public boolean hasSameEnvironment(EnvironmentPhoto other){
		return environment == other.environment;
	}

    public EnvironmentPhoto() {
		super();
	}

	/**
	 * 
	 * @methodtype constructor
	 */
	public EnvironmentPhoto(PhotoId myId) {
        super(myId);
    }
    
	/**
	 * 
	 * @methodtype constructor
	 */
	public EnvironmentPhoto(ResultSet rset) throws SQLException {
		readFrom(rset);
    }
    
    @Override
    public void writeOn(ResultSet rset) throws SQLException {
		super.writeOn(rset);
		rset.updateString("environment", environment.toString());
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        super.readFrom(rset);
		environment = Environment.getEnvironmentFromString(rset.getString("environment"));
	}
	
}

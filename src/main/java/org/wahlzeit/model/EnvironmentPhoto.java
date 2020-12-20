package org.wahlzeit.model;

import java.sql.*;

public class EnvironmentPhoto extends Photo {

	protected Environment environment = Environment.DEFAULT;

	public boolean hasSameEnvironment(EnvironmentPhoto other) {
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
		try {

			rset.updateString("environment", environment.toString());
		} catch (SQLException e) {
			rset.cancelRowUpdates();
		}
	}

	@Override
	public void readFrom(ResultSet rset) {
		super.readFrom(rset);
		try {
			environment = Environment.getEnvironmentFromString(rset.getString("environment"));
		} catch (SQLException e) {
			environment = Environment.DEFAULT;
		}
	}

}

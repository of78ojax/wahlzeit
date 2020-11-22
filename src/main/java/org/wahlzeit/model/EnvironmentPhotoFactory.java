package org.wahlzeit.model;
import java.sql.*;

public class EnvironmentPhotoFactory extends PhotoFactory {

	@Override
    public EnvironmentPhoto createPhoto() {
		return new EnvironmentPhoto();
	}
	
	/**
	 * 
	 */
	@Override
	public EnvironmentPhoto createPhoto(PhotoId id) {
		return new EnvironmentPhoto(id);
	}
	
	/**
	 * 
	 */
	@Override
	public EnvironmentPhoto createPhoto(ResultSet rs) throws SQLException {
		return new EnvironmentPhoto(rs);
	}
}

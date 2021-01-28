package org.wahlzeit.model;
import java.sql.*;

public class EnvironmentPhotoFactory extends PhotoFactory {


	/**
	 * @methodtype factory
	 * default factory mehtod creates default EnvPhoto
	 */
	@Override
    public EnvironmentPhoto createPhoto() {
		return new EnvironmentPhoto();
	}
	
	/**
	 *  factory mehtod creates default EnvPhoto with specific id
	 */
	@Override
	public EnvironmentPhoto createPhoto(PhotoId id) {
		return new EnvironmentPhoto(id);
	}
	
	/**
	 * factory mehtod creates Envphoto from resultset
	 */
	@Override
	public EnvironmentPhoto createPhoto(ResultSet rs) throws SQLException {
		return new EnvironmentPhoto(rs);
	}
}

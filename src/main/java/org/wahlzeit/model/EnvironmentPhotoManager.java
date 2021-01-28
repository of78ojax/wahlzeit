package org.wahlzeit.model;
import java.io.*;
import java.sql.*;

public class EnvironmentPhotoManager extends PhotoManager{
    
    @Override
    protected EnvironmentPhoto getPhotoFromFilter(PhotoFilter filter) {
        return (EnvironmentPhoto) super.getPhotoFromFilter(filter);

    }

    // Overrides but calls super-class Mehtod createObject
    @Override
    public EnvironmentPhoto createPhoto(File file) throws Exception {
		return (EnvironmentPhoto) super.createPhoto(file);
    }

    // Overrides but calls super-class Mehtod createObject
    @Override
    protected EnvironmentPhoto createObject(ResultSet rset) throws SQLException {
		return (EnvironmentPhoto) PhotoFactory.getInstance().createPhoto(rset);
	}

    public static EnvironmentPhoto getPhoto(String id) {
		return (EnvironmentPhoto) getPhoto(PhotoId.getIdFromString(id));
	}
	
	public static EnvironmentPhoto getPhoto(PhotoId id) {
		return (EnvironmentPhoto) instance.getPhotoFromId(id);
    }
    
    @Override 
    protected EnvironmentPhoto doGetPhotoFromId(PhotoId id) {
        return (EnvironmentPhoto) super.doGetPhotoFromId(id);
    }

    @Override
    public EnvironmentPhoto getPhotoFromId(PhotoId id){
        return (EnvironmentPhoto) super.getPhotoFromId(id);
    }

    
}

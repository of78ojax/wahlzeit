/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import java.sql.*;
import java.util.concurrent.locks.StampedLock;
import java.net.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

/**
 * A photo represents a user-provided (uploaded) photo.
 */
public class Photo extends DataObject {

	/**
	 * 
	 */
	public static final String IMAGE = "image";
	public static final String THUMB = "thumb";
	public static final String LINK = "link";
	public static final String PRAISE = "praise";
	public static final String NO_VOTES = "noVotes";
	public static final String CAPTION = "caption";
	public static final String DESCRIPTION = "description";
	public static final String KEYWORDS = "keywords";

	public static final String TAGS = "tags";

	public static final String STATUS = "status";
	public static final String IS_INVISIBLE = "isInvisible";
	public static final String UPLOADED_ON = "uploadedOn";

	/**
	 * 
	 */
	public static final int MAX_PHOTO_WIDTH = 420;
	public static final int MAX_PHOTO_HEIGHT = 600;
	public static final int MAX_THUMB_PHOTO_WIDTH = 105;
	public static final int MAX_THUMB_PHOTO_HEIGHT = 150;

	/**
	 * 
	 */
	protected PhotoId id = null;

	/**
	 * 
	 */
	protected int ownerId = 0;
	protected String ownerName;

	/**
	 * 
	 */
	protected boolean ownerNotifyAboutPraise = false;
	protected EmailAddress ownerEmailAddress = EmailAddress.EMPTY;
	protected Language ownerLanguage = Language.ENGLISH;
	protected URL ownerHomePage;

	/**
	 * 
	 */
	protected int width;
	protected int height;
	protected PhotoSize maxPhotoSize = PhotoSize.MEDIUM; // derived

	/**
	 * 
	 */
	protected Tags tags = Tags.EMPTY_TAGS;

	/**
	 * 
	 */
	protected PhotoStatus status = PhotoStatus.VISIBLE;

	/**
	 * 
	 */
	protected int praiseSum = 10;
	protected int noVotes = 1;

	/**
	 * 
	 */
	protected long creationTime = System.currentTimeMillis();

	Location location;

	/**
	 * 
	 */
	public Photo() {
		id = PhotoId.getNextId();
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype constructor
	 */
	public Photo(PhotoId myId) {
		id = myId;

		incWriteCount();
	}

	/**
	 * 
	 * @methodtype constructor
	 */
	public Photo(ResultSet rset) throws SQLException {
		readFrom(rset);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getIdAsString() {
		return String.valueOf(id.asInt());
	}

	/**
	 * 
	 */
	public void readFrom(ResultSet rset) throws SQLException {
		id = PhotoId.getIdFromInt(rset.getInt("id"));

		ownerId = rset.getInt("owner_id");
		ownerName = rset.getString("owner_name");

		ownerNotifyAboutPraise = rset.getBoolean("owner_notify_about_praise");
		ownerEmailAddress = EmailAddress.getFromString(rset.getString("owner_email_address"));
		ownerLanguage = Language.getFromInt(rset.getInt("owner_language"));
		ownerHomePage = StringUtil.asUrl(rset.getString("owner_home_page"));

		width = rset.getInt("width");
		height = rset.getInt("height");

		tags = new Tags(rset.getString("tags"));

		status = PhotoStatus.getFromInt(rset.getInt("status"));
		praiseSum = rset.getInt("praise_sum");
		noVotes = rset.getInt("no_votes");

		creationTime = rset.getLong("creation_time");

		maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);
		String txt = rset.getString("location_name");
		if (txt != null) {
			if (location == null) {
				location = new Location();
			}
			location.setLocationName(txt);
			location.setCoords(rset.getDouble("loc_x"), rset.getDouble("loc_y"), rset.getDouble("loc_z"));
		}
	}

	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateInt("id", id.asInt());
		rset.updateInt("owner_id", ownerId);
		rset.updateString("owner_name", ownerName);
		rset.updateBoolean("owner_notify_about_praise", ownerNotifyAboutPraise);
		rset.updateString("owner_email_address", ownerEmailAddress.asString());
		rset.updateInt("owner_language", ownerLanguage.asInt());
		rset.updateString("owner_home_page", ownerHomePage.toString());
		rset.updateInt("width", width);
		rset.updateInt("height", height);
		rset.updateString("tags", tags.asString());
		rset.updateInt("status", status.asInt());
		rset.updateInt("praise_sum", praiseSum);
		rset.updateInt("no_votes", noVotes);
		rset.updateLong("creation_time", creationTime);
		if (location != null) {
			rset.updateString("location_name", location.getLocationName());
			double[] coords = location.getCoords();
			rset.updateDouble("loc_x", coords[0]);
			rset.updateDouble("loc_y", coords[1]);
			rset.updateDouble("loc_z", coords[2]);
		}

	}

	public boolean hasLocation() {
		return (location != null);
	}

	public void setLocation(Location loc) {
		location = loc;
	}

	public void setLocation(String name, double x, double y, double z) {
		location = new Location();
		location.setLocationName(name);
		location.setCoords(x, y, z);
	}

	public void setLocationName(String name) {
		if (location != null)
			location.setLocationName(name);
	}

	public String getLocationName() {
		if (location != null) {
			return location.getLocationName();
		} else {
			return "";
		}
	}

	public void setCoords(double x, double y, double z) {
		if (location != null)
			location.setCoords(x, y, z);
	}

	public double[] getCoords() {
		if (location != null) {
			return location.getCoords();
		} else {
			double tmp[] = { -1., -1., -1. };
			return tmp;
		}
	}

	public boolean hasSameLocation(Photo other) {
		if (location != null && other.location != null) {
			return location.equals(other.location);
		} else {
			return false;
		}
	}

	public boolean takenCloseTogehter(Photo other) {
		if (location != null && other.location != null) {
			return this.location.isClose(other.location);
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	public void writeId(PreparedStatement stmt, int pos) throws SQLException {
		stmt.setInt(pos, id.asInt());
	}

	/**
	 * 
	 * @methodtype get
	 */
	public PhotoId getId() {
		return id;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerId(int newId) {
		ownerId = newId;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerName(String newName) {
		ownerName = newName;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getSummary(ModelConfig cfg) {
		return cfg.asPhotoSummary(ownerName);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getCaption(ModelConfig cfg) {
		return cfg.asPhotoCaption(ownerName, ownerHomePage);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public boolean getOwnerNotifyAboutPraise() {
		return ownerNotifyAboutPraise;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerNotifyAboutPraise(boolean newNotifyAboutPraise) {
		ownerNotifyAboutPraise = newNotifyAboutPraise;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public EmailAddress getOwnerEmailAddress() {
		return ownerEmailAddress;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerEmailAddress(EmailAddress newEmailAddress) {
		ownerEmailAddress = newEmailAddress;
		incWriteCount();
	}

	/**
	 * 
	 */
	public Language getOwnerLanguage() {
		return ownerLanguage;
	}

	/**
	 * 
	 */
	public void setOwnerLanguage(Language newLanguage) {
		ownerLanguage = newLanguage;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public URL getOwnerHomePage() {
		return ownerHomePage;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerHomePage(URL newHomePage) {
		ownerHomePage = newHomePage;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasSameOwner(Photo photo) {
		return photo.getOwnerEmailAddress().equals(ownerEmailAddress);
	}

	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean isWiderThanHigher() {
		return (height * MAX_PHOTO_WIDTH) < (width * MAX_PHOTO_HEIGHT);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public int getThumbWidth() {
		return isWiderThanHigher() ? MAX_THUMB_PHOTO_WIDTH : (width * MAX_THUMB_PHOTO_HEIGHT / height);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public int getThumbHeight() {
		return isWiderThanHigher() ? (height * MAX_THUMB_PHOTO_WIDTH / width) : MAX_THUMB_PHOTO_HEIGHT;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setWidthAndHeight(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;

		maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

		incWriteCount();
	}

	/**
	 * Can this photo satisfy provided photo size?
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasPhotoSize(PhotoSize size) {
		return maxPhotoSize.asInt() >= size.asInt();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public PhotoSize getMaxPhotoSize() {
		return maxPhotoSize;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getPraise() {
		return (double) praiseSum / noVotes;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getPraiseAsString(ModelConfig cfg) {
		return cfg.asPraiseString(getPraise());
	}

	/**
	 * 
	 */
	public void addToPraise(int value) {
		praiseSum += value;
		noVotes += 1;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean isVisible() {
		return status.isDisplayable();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public PhotoStatus getStatus() {
		return status;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setStatus(PhotoStatus newStatus) {
		status = newStatus;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasTag(String tag) {
		return tags.hasTag(tag);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public Tags getTags() {
		return tags;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setTags(Tags newTags) {
		tags = newTags;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public long getCreationTime() {
		return creationTime;
	}

}

package com.claymus.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AuditLogData implements Serializable { 
	
	private Long id;
	
	private String eventId;
	
	private String eventDataOld;

	private String eventDataNew;
	
	private AccessTokenData accessToken;
	
	private Date creationDate;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId( String eventId ) {
		this.eventId = eventId;
	}
	
	public String getEventDataOld() {
		return eventDataOld;
	}

	public void setEventDataOld( String eventDataOld ) {
		this.eventDataOld = eventDataOld;
	}

	public String getEventDataNew() {
		return eventDataNew;
	}

	public void setEventDataNew( String eventDataNew ) {
		this.eventDataNew = eventDataNew;
	}

	public AccessTokenData getAccessToken() {
		return accessToken;
	}

	public void setAccessToken( AccessTokenData accessToken ) {
		this.accessToken = accessToken;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}

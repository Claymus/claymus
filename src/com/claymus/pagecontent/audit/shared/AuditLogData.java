package com.claymus.pagecontent.audit.shared;

import java.util.Date;

public class AuditLogData { 
	
	private Long id;
	
	private String eventId;
	
	private String eventDataOld;

	private String eventDataNew;
	
	private String accessId;
	
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

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}

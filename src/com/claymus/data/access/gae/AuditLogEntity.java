package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.AuditLog;

@SuppressWarnings("serial")
@PersistenceCapable( table = "AUDIT_LOG" )
public class AuditLogEntity implements AuditLog {

	@PrimaryKey
	@Persistent( column = "AUDIT_LOG_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "EVENT_ID" )
	private String eventId;
	
	@Persistent( column = "EVENT_DATA_OLD" )
	private String eventDataOld;
	
	@Persistent( column = "EVENT_DATA_NEW" )
	private String eventDataNew;
	
	@Persistent( column = "ACCESS_ID" )
	private String accessId;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getEventId() {
		return eventId;
	}

	@Override
	public void setEventId( String eventId ) {
		this.eventId = eventId;
	}

	@Override
	public String getEventDataOld() {
		return eventDataOld;
	}

	@Override
	public void setEventDataOld( String eventDataOld ) {
		this.eventDataOld = eventDataOld;
	}

	@Override
	public String getEventDataNew() {
		return eventDataNew;
	}

	@Override
	public void setEventDataNew( String eventDataNew ) {
		this.eventDataNew = eventDataNew;
	}

	@Override
	public String getAccessId() {
		return accessId;
	}

	@Override
	public void setAccessId( String accessId ) {
		this.accessId = accessId;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}

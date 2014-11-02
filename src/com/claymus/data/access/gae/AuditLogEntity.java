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
	
	@Persistent( column = "EVENT_NAME" )
	private String eventName;
	
	@Persistent( column = "EVENT_ID" )
	private String entityId;
	
	@Persistent( column = "EVENT_DATA_OLD" )
	private String entityDataOld;
	
	@Persistent( column = "EVENT_DATA_NEW" )
	private String entityDataNew;
	
	@Persistent( column = "USER_ID" )
	private String userId;
	
	@Persistent( column = "DATE" )
	private Date date;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getEventName() {
		return eventName;
	}

	@Override
	public void setEventName( String eventName ) {
		this.eventName = eventName;
	}

	@Override
	public String getEntityId() {
		return entityId;
	}

	@Override
	public void setEntityId( String entityId ) {
		this.entityId = entityId;
	}

	@Override
	public String getEntityDataOld() {
		return entityDataOld;
	}

	@Override
	public void setEntityDataOld( String entityDataOld ) {
		this.entityDataOld = entityDataOld;
	}

	@Override
	public String getEntityDataNew() {
		return entityDataNew;
	}

	@Override
	public void setEntityDataNew( String entityDataNew ) {
		this.entityDataNew = entityDataNew;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId( String userId ) {
		this.userId = userId;
	}

	@Override
	public Date getDate() {
		return date;
	}

	public void setDate( Date date ) {
		this.date = date;
	}

}

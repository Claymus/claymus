package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.AuditLog;
import com.google.appengine.api.datastore.Text;
import com.pratilipi.common.type.AccessType;

@PersistenceCapable( table = "AUDIT_LOG" )
public class AuditLogEntity implements AuditLog {

	private static final long serialVersionUID = -2027252778248254540L;

	@PrimaryKey
	@Persistent( column = "AUDIT_LOG_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "ACCESS_ID" )
	private String accessId;
	
	@Persistent( column = "ACCESS_TYPE" )
	private AccessType accessType;

	@Persistent( column = "EVENT_ID" )
	private String eventId;
	
	@Persistent( column = "EVENT_DATA_OLD" )
	private Text eventDataOld;
	
	@Persistent( column = "EVENT_DATA_NEW" )
	private Text eventDataNew;
	
	@Persistent( column = "EVENT_COMMENT" )
	private Text eventComment;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	
	@Override
	public Long getId() {
		return id;
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
	public String getEventId() {
		return eventId;
	}

	@Override
	public void setEventId( String eventId ) {
		this.eventId = eventId;
	}

	@Override
	public String getEventDataOld() {
		return eventDataOld == null ? null : eventDataOld.getValue();
	}

	@Override
	public void setEventDataOld( String eventDataOld ) {
		this.eventDataOld = eventDataOld == null ? null : new Text( eventDataOld );
	}

	@Override
	public String getEventDataNew() {
		return eventDataNew == null ? null : eventDataNew.getValue();
	}

	@Override
	public void setEventDataNew( String eventDataNew ) {
		this.eventDataNew = eventDataNew == null ? null : new Text( eventDataNew );
	}

	@Override
	public String getEventComment() {
		return eventComment == null ? null : eventComment.getValue();
	}

	@Override
	public void setEventComment( String eventComment ) {
		this.eventComment = eventComment == null ? null : new Text( eventComment );
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}

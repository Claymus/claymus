package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface AuditLog extends Serializable {
	
	Long getId();

	String getAccessId();

	void setAccessId( String accessId );
	
	String getEventId();
	
	void setEventId( String eventId );

	String getEventDataOld();
	
	void setEventDataOld( String eventDataOld );

	String getEventDataNew();

	void setEventDataNew(  String eventDataNew );

	String getEventComment();
	
	void setEventComment( String eventComment );

	Date getCreationDate();

}
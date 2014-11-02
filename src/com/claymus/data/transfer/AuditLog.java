package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface AuditLog extends Serializable {
	
	Long getId();

	String getEventName();

	void setEventName( String eventName );
	
	String getEntityId();
	
	void setEntityId( String entityId );

	String getEntityDataOld();
	
	void setEntityDataOld( String entityDataOld );

	String getEntityDataNew();

	void setEntityDataNew(  String entityDataNew );

	String getUserId();

	void setUserId( String userId );
	
	Date getDate();

}
package com.claymus.data.transfer;

import java.io.Serializable;

public interface RoleAccess extends Serializable {

	String getId();
	
	String getRoleId();

	void setRoleId( String id );
	
	String getAccessId();

	void setAccessId( String id );

	boolean hasAccess();
	
	void setAccess( boolean access );
	
}

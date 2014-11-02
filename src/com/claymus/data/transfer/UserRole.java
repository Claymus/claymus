package com.claymus.data.transfer;

import java.io.Serializable;

public interface UserRole extends Serializable {

	String getId();
	
	Long getUserId();

	void setUserId( Long userId );
	
	String getRoleId();

	void setRoleId( String roleId );
	
}

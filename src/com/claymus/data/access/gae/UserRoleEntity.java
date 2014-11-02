package com.claymus.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.UserRole;

@SuppressWarnings("serial")
@PersistenceCapable( table = "USER_ROLE" )
public class UserRoleEntity implements UserRole {
	
	@PrimaryKey
	@Persistent( column = "USER_ROLE_ID" )
	private String id;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "ROLE_ID" )
	private String roleId;
	
	
	@Override
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public String getRoleId() {
		return roleId;
	}

	@Override
	public void setRoleId( String roleId ) {
		this.roleId = roleId;
	}

}

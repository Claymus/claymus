package com.claymus.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.RoleAccess;

@SuppressWarnings("serial")
@PersistenceCapable( table = "ROLE_ACCESS" )
public class RoleAccessEntity implements RoleAccess {
	
	@PrimaryKey
	@Persistent( column = "ROLE_ACCESS_ID" )
	private String id;
	
	@Persistent( column = "ROLE_ID" )
	private String roleId;
	
	@Persistent( column = "ACCESS_ID" )
	private String accessId;
	
	@Persistent( column = "ACCESS" )
	private Boolean access;

	
	@Override
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	@Override
	public String getRoleId() {
		return roleId;
	}

	@Override
	public void setRoleId( String roleId ) {
		this.roleId = roleId;
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
	public boolean hasAccess() {
		return access == null ? false : access;
	}

	@Override
	public void setAccess( boolean access ) {
		this.access = access;
	}

}

package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SaveRoleAccessRequest implements IsSerializable {

	private String roleId;
	
	private String accessId;
	
	private Boolean access;
	

	@SuppressWarnings("unused")
	private SaveRoleAccessRequest() {}
	
	public SaveRoleAccessRequest( String roleId, String accessId, Boolean access ) {
		this.roleId = roleId;
		this.accessId = accessId;
		this.access = access;
	}
	
	
	public String getRoleId() {
		return roleId;
	}

	public String getAccessId() {
		return accessId;
	}

	public Boolean getAccess() {
		return access;
	}
	
}

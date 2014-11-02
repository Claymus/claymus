package com.claymus.service.shared.data;

import com.claymus.commons.shared.UserStatus;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserData implements IsSerializable {

	private Long id;
	
	private String password;
	
	private String firstName;
	
	private String lastName;

	private String email;
	
	private String campaign;
	
	private String referer;
	
	private UserStatus status;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	public String getName() {
		if( firstName == null )
			return email;
		
		return lastName == null ? firstName : firstName + " " + lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign( String campaign ) {
		this.campaign = campaign;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer( String referer ) {
		this.referer = referer;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus( UserStatus status ) {
		this.status = status;
	}
	
}

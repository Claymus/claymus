package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.claymus.commons.shared.UserStatus;
import com.pratilipi.commons.shared.UserSex;

public interface User extends Serializable {

	Long getId();

	void setId( Long id );
	
	String getPassword();

	void setPassword( String password );

	String getFirstName();

	void setFirstName( String firstName );

	String getLastName();

	void setLastName( String lastName );

	String getNickName();

	void setNickName( String nickName );

	String getEmail();

	void setEmail( String email );
	
	Date getDateOfBirth();
	
	void setDateOfBirth( Date dateOfBirth );
	
	UserSex getSex();
	
	void setSex( UserSex userSex );

	String getPhone();

	void setPhone( String phone );

	String getCampaign();

	void setCampaign( String campaign );
	
	String getReferer();

	String setReferer( String referer );
	
	Date getSignUpDate();
	
	void setSignUpDate( Date date );

	UserStatus getStatus();
	
	void setStatus( UserStatus userStatus );
	
}

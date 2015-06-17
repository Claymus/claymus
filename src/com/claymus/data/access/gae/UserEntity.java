package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.commons.shared.UserStatus;
import com.claymus.data.transfer.User;
import com.pratilipi.commons.shared.UserSex;

@SuppressWarnings("serial")
@PersistenceCapable( table = "USER" )
public class UserEntity implements User {
	
	@PrimaryKey
	@Persistent( column = "USER_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PASSWORD" )
	private String password;
	
	@Persistent( column = "FIRST_NAME" )
	private String firstName;
	
	@Persistent( column = "LAST_NAME" )
	private String lastName;
	
	@Persistent( column = "NICK_NAME" )
	private String nickName;
	
	@Persistent( column = "EMAIL" )
	private String email;
	
	@Persistent( column = "DATE_OF_BIRTH" )
	private Date dateOfBirth;
	
	@Persistent( column = "SEX" )
	private UserSex sex;
	
	@Persistent( column = "PHONE" )
	private String phone;
	
	@Persistent( column = "CAMPAIGN" )
	private String campaign;
	
	@Persistent( column = "REFERER" )
	private String referer;
	
	@Persistent( column = "STATUS" )
	private UserStatus status;
	
	@Persistent( column = "SIGN_UP_DATE" )
	private Date signUpDate;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId( Long id ) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword( String password ) {
		this.password = password;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	@Override
	public String getNickName() {
		return nickName;
	}

	@Override
	public void setNickName( String nickName ) {
		this.nickName = nickName;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail( String email ) {
		this.email = email;
	}
	
	@Override
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public void setDateOfBirth( Date dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Override
	public UserSex getSex() {
		return sex;
	}

	@Override
	public void setSex( UserSex sex ) {
		this.sex = sex;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public void setPhone( String phone ) {
		this.phone = phone;
	}

	@Override
	public String getCampaign() {
		return campaign;
	}

	@Override
	public void setCampaign( String campaign ) {
		this.campaign = campaign;
	}

	@Override
	public String getReferer() {
		return referer;
	}

	@Override
	public String setReferer( String referer ) {
		return this.referer = referer;
	}

	@Override
	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate( Date signUpDate ) {
		this.signUpDate = signUpDate;
	}

	@Override
	public UserStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus( UserStatus status ) {
		this.status = status;
	}

}

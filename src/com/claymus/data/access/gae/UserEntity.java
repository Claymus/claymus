package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.commons.shared.UserSignUpSource;
import com.claymus.commons.shared.UserState;
import com.claymus.commons.shared.UserStatus;
import com.claymus.data.transfer.User;
import com.pratilipi.common.type.Gender;

@PersistenceCapable( table = "USER" )
public class UserEntity implements User {
	
	private static final long serialVersionUID = 5942981653445086715L;

	@PrimaryKey
	@Persistent( column = "USER_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "FACEBOOK_ID" )
	private String facebookId;
	
	@Persistent( column = "PASSWORD" )
	private String password;
	
	
	@Persistent( column = "FIRST_NAME" )
	private String firstName;
	
	@Persistent( column = "LAST_NAME" )
	private String lastName;
	
	@Persistent( column = "NICK_NAME" )
	private String nickName;
	

	@Persistent( column = "GENDER" )
	private Gender gender;
	
	@Persistent( column = "DATE_OF_BIRTH" )
	private Date dateOfBirth;
	
	@Persistent( column = "EMAIL" )
	private String email;
	
	@Persistent( column = "PHONE" )
	private String phone;

	
	@Deprecated
	@Persistent( column = "CAMPAIGN" )
	private String campaign;
	
	@Deprecated
	@Persistent( column = "REFERER" )
	private String referer;
	
	@Deprecated
	@Persistent( column = "STATUS" )
	private UserStatus status;
	
	@Persistent( column = "VERIFICATION_TOKEN" )
	private String verificationToken;
	
	@Persistent( column = "STATE" )
	private UserState state;


	@Persistent( column = "SIGN_UP_DATE" )
	private Date signUpDate;
	
	@Persistent( column = "SIGN_UP_SOURCE" )
	private UserSignUpSource signUpSource;

	
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
	public Gender getGender() {
		return gender;
	}

	@Override
	public void setGender( Gender sex ) {
		this.gender = sex;
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
	public UserStatus getStatus() {
		
		if( status != null )
			return status;
		
		switch( state ) {
			case REFERRAL:
				if( signUpSource == UserSignUpSource.PRE_LAUNCH_WEBSITE )
					return UserStatus.PRELAUNCH_REFERRAL;
				else
					return UserStatus.POSTLAUNCH_REFERRAL;
			case REGISTERED:
			case ACTIVE:
				switch( signUpSource ) {
					case PRE_LAUNCH_WEBSITE:
						return UserStatus.PRELAUNCH_SIGNUP;
					case WEBSITE:
					case WEBSITE_M6:
					case WEBSITE_M6_TAMIL:
						return UserStatus.POSTLAUNCH_SIGNUP;
					case WEBSITE_FACEBOOK:
					case WEBSITE_M6_FACEBOOK:
					case WEBSITE_M6_TAMIL_FACEBOOK:
						return UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN;
					case ANDROID_APP:
						return UserStatus.ANDROID_SIGNUP;
					case ANDROID_APP_FACEBOOK:
						return UserStatus.ANDROID_SIGNUP_FACEBOOK;
					case ANDROID_APP_GOOGLE:
						return UserStatus.ANDROID_SIGNUP_GOOGLE;
				}
			case BLOCKED:
			default:
				return null;
		}
		
	}

	@Override
	public void setStatus( UserStatus status ) {
		this.status = status;
	}
	
	@Override
	public UserState getState() {
		if( state != null )
			return state;
		
		switch( status ) {
			case PRELAUNCH_REFERRAL:
			case POSTLAUNCH_REFERRAL:
				return UserState.REFERRAL;
			case PRELAUNCH_SIGNUP:
			case POSTLAUNCH_SIGNUP:
			case POSTLAUNCH_SIGNUP_SOCIALLOGIN:
			case ANDROID_SIGNUP:
			case ANDROID_SIGNUP_FACEBOOK:
			case ANDROID_SIGNUP_GOOGLE:
				return UserState.REGISTERED;
			default:
				return null;
		}
		
	}

	@Override
	public void setState( UserState state ) {
		this.state = state;
	}
	
	@Override
	public Date getSignUpDate() {
		return signUpDate;
	}

	@Override
	public void setSignUpDate( Date signUpDate ) {
		this.signUpDate = signUpDate;
	}
	
	@Override
	public UserSignUpSource getSignUpSource() {
		if( signUpSource != null )
			return signUpSource;
		
		switch( status ) {
			case PRELAUNCH_REFERRAL:
			case PRELAUNCH_SIGNUP:
				return UserSignUpSource.PRE_LAUNCH_WEBSITE;
			case POSTLAUNCH_REFERRAL:
			case POSTLAUNCH_SIGNUP:
				return UserSignUpSource.WEBSITE;
			case POSTLAUNCH_SIGNUP_SOCIALLOGIN:
				return UserSignUpSource.WEBSITE_FACEBOOK;
			case ANDROID_SIGNUP:
				return UserSignUpSource.ANDROID_APP;
			case ANDROID_SIGNUP_FACEBOOK:
				return UserSignUpSource.ANDROID_APP_FACEBOOK;
			case ANDROID_SIGNUP_GOOGLE:
				return UserSignUpSource.ANDROID_APP_GOOGLE;
			default:
				return null;
		}
		
	}

	@Override
	public void setSignUpSource( UserSignUpSource signUpSource ) {
		this.signUpSource = signUpSource;
	}

}

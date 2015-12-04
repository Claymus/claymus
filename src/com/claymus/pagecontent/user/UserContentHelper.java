package com.claymus.pagecontent.user;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.server.FacebookUtil;
import com.claymus.commons.server.GoogleUtil;
import com.claymus.commons.shared.UserStatus;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.data.transfer.shared.UserData;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.user.gae.UserContentEntity;
import com.claymus.pagecontent.user.shared.UserContentData;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.claymus.taskqueue.TaskQueueFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.pratilipi.common.type.Gender;
import com.pratilipi.data.type.AccessToken;

public class UserContentHelper extends PageContentHelper<
		UserContent,
		UserContentData,
		UserContentProcessor> {
	
	private static final Access ACCESS_TO_LIST_USER_DATA =
			new Access( "user_data_list", false, "View User Data" );
	
	private final static Logger logger = Logger.getLogger( UserContentHelper.class.getName() );

	
	@Override
	public String getModuleName() {
		return "User";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_USER_DATA };
	}
	

	public static UserContent newUserContent() {
		return new UserContentEntity();
	}

	public static boolean isAdmin( HttpServletRequest request ){
		
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		List<UserRole> userRoleList = DataAccessorFactory.getDataAccessor( request )
											.getUserRoleList( accessToken.getUserId() );
		for( UserRole userRole : userRoleList ){
			if( userRole.getRoleId().equals( "administrator" ))
				return true;
		}
		
		return false;
	}
	
	public static boolean hasRequestAccessToListUserData( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_LIST_USER_DATA );
	}
	
	public static AccessToken googleLogin( String googleAccessToken, String socialId, HttpServletRequest request ) 
			throws IOException, InvalidArgumentException, GeneralSecurityException, JSONException{
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		Map<String, String> googleCredentials = dataAccessor.getAppProperty( GoogleUtil.APP_PROPERTY_ID ).getValue();
		GoogleUtil googleUtil = new GoogleUtil( googleCredentials );
		if( !googleUtil.isValid( googleAccessToken, socialId ) )
			throw new InvalidArgumentException( "Invalid Access Token" );
		
		Map<String, String> userDataMap = googleUtil.getUserProfile( googleAccessToken, socialId );
		User user = dataAccessor.getUserByEmail( userDataMap.get( "user_email" ));
		UserData userData = new UserData();
		AccessToken accessToken;

		if( user == null ){
			userData.setName( userDataMap.get( GoogleUtil.GOOGLE_USER_DISPLAYNAME ));
			userData.setEmail( userDataMap.get( GoogleUtil.GOOGLE_USER_EMAIL ));
			if( userDataMap.get( GoogleUtil.GOOGLE_USER_GENDER ).toUpperCase().equals( Gender.MALE.toString() ))
				userData.setGender( Gender.MALE );
			else if( userDataMap.get( GoogleUtil.GOOGLE_USER_GENDER ).toUpperCase().equals( Gender.FEMALE.toString() ))
				userData.setGender( Gender.FEMALE );
			else
				userData.setGender( Gender.OTHER );
			userData.setStatus( UserStatus.ANDROID_SIGNUP_GOOGLE );
			accessToken = registerUser( userData, request );
		} else{
			if( user.getStatus() != UserStatus.ANDROID_SIGNUP_GOOGLE ){
				user.setStatus( UserStatus.ANDROID_SIGNUP_GOOGLE );
			}
			
			user = dataAccessor.createOrUpdateUser( user );
			accessToken = ClaymusHelper.get( request ).createAccessToken( user.getId() );
		}

		return accessToken;
		
	}
	
	public static AccessToken facebookLogin( String socialId, String fbAccessToken, HttpServletRequest request ) 
			throws JSONException, IOException, InvalidArgumentException{
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Map<String, String> fbCredentials = dataAccessor.getAppProperty( FacebookUtil.APP_PROPERTY_ID ).getValue();
		FacebookUtil fbUtil = new FacebookUtil( fbCredentials );
		if( !fbUtil.validateAccessToken( socialId, fbAccessToken) )
			throw new InvalidArgumentException( "Invalid Access Token" );
		
		Map<String, String> userDataMap = fbUtil.getUserProfile( socialId, fbAccessToken );
		User user = dataAccessor.getUserByEmail( userDataMap.get( FacebookUtil.FACEBOOK_USER_EMAIL ) );
		UserData userData = new UserData();
		AccessToken accessToken;
		
	
		if( user == null ){
			userData.setName( userDataMap.get( FacebookUtil.FACEBOOK_USER_NAME ));
			userData.setEmail( userDataMap.get( FacebookUtil.FACEBOOK_USER_EMAIL ));
			if( userDataMap.get( FacebookUtil.FACEBOOK_USER_GENDER ).toUpperCase().equals( Gender.MALE.toString() ))
				userData.setGender( Gender.MALE );
			else if( userDataMap.get( FacebookUtil.FACEBOOK_USER_GENDER ).toUpperCase().equals( Gender.FEMALE.toString() ))
				userData.setGender( Gender.FEMALE );
			else
				userData.setGender( Gender.OTHER );
			userData.setStatus( UserStatus.ANDROID_SIGNUP_FACEBOOK );
			accessToken = registerUser( userData, request );
		} else {
			if( user.getStatus() != UserStatus.ANDROID_SIGNUP_FACEBOOK ){
				user.setStatus( UserStatus.ANDROID_SIGNUP_FACEBOOK );
			}
			user = dataAccessor.createOrUpdateUser( user );
			accessToken = ClaymusHelper.get( request ).createAccessToken( user.getId() );
		}
		return accessToken;
	}
	

	public static AccessToken userLogin( UserData userData, HttpServletRequest request ) 
			throws InvalidArgumentException{
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = dataAccessor.newAccessToken();
		User user = dataAccessor.getUserByEmail( userData.getEmail() );
		
		if( user == null )
			throw new InvalidArgumentException( "Invalid User ID" );
		if( user != null && user.getPassword() == null )
			throw new InvalidArgumentException( "You are registered via social login. Please use the same to login." );
		
		if( user != null && userData.getPassword() != null ) {
			if( !EncryptPassword.check( userData.getPassword(), user.getPassword() ) )
				throw new InvalidArgumentException( "Invalid user secret." );
			
			ClaymusHelper claymusHelper = ClaymusHelper.get( request );
			accessToken = claymusHelper.createAccessToken( user.getId() ); 
		} else
			throw new InvalidArgumentException( "User Secret cannot be empty" );
		
		return accessToken;
		
	}
	
	
	public static UserData createUserData( Long userId, HttpServletRequest request ){
		return createUserData( DataAccessorFactory.getDataAccessor( request ).getUser( userId ) );
	}
	
	public static UserData createUserData( String email, HttpServletRequest request ){
		return createUserData( DataAccessorFactory.getDataAccessor( request ).getUserByEmail( email ) );
	}
	
	public static UserData createUserData( User user ){
		
		UserData userData = new UserData();
		userData.setId( user.getId() );
		userData.setFirstName( user.getFirstName() );
		userData.setLastName( user.getLastName() );
		String name = null;
		if( userData.getFirstName() == null && userData.getLastName() == null )
			name = userData.getEmail();
		else if( userData.getFirstName() != null && userData.getLastName() == null )
			name = userData.getFirstName();
		else if( userData.getFirstName() != null && userData.getLastName() != null )
			name = userData.getFirstName() + " " + userData.getLastName();
		userData.setName( name );
		userData.setEmail( user.getEmail() );
		userData.setDateOfBirth( user.getDateOfBirth() );
		userData.setGender( user.getGender() );
		userData.setCampaign( user.getCampaign() );
		userData.setReferer( user.getReferer() );
		userData.setStatus( user.getStatus() );
		
		return userData;
	}
	
	public static AccessToken registerUser( UserData userData, HttpServletRequest request ) 
			throws InvalidArgumentException{
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		User user = dataAccessor.getUserByEmail( userData.getEmail() );
		
		if( user == null ) {
			user = dataAccessor.newUser();
			user.setEmail( userData.getEmail().toLowerCase() );
			user.setSignUpDate( new Date() );

			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			
		} else if( user.getStatus() == UserStatus.PRELAUNCH_REFERRAL ) {
			user.setCampaign( userData.getCampaign() );
			if( userData.getReferer() != null )
				user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );

		} else if( user.getStatus() == UserStatus.PRELAUNCH_SIGNUP ) {
			
		} else if( user.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			user.setCampaign( userData.getCampaign() );
			if( userData.getReferer() != null )
				user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );

		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP || user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN ) {
			throw new InvalidArgumentException( "This email id is already registered !" );

		} else {
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new InvalidArgumentException( "User registration failed ! "
					+ "Kindly contact the administrator." );
		}
		
		String firstName = null;
		String lastName = null;
		if( userData.getName() != null && userData.getName().trim().lastIndexOf( " " ) != -1 ){
			firstName = userData.getName().trim().substring( 0, userData.getName().trim().lastIndexOf( " " ));
			lastName = userData.getName().trim().substring( userData.getName().trim().lastIndexOf( " " ) + 1 );
		} else if( userData.getName() != null && userData.getName().trim().lastIndexOf( " " ) == -1 ){
			firstName = userData.getName();
		} else{
			firstName = userData.getFirstName();
			lastName = userData.getLastName();
		}
		
		user.setFirstName( firstName );
		user.setLastName( lastName );
		if( userData.getPassword() != null )
			user.setPassword( EncryptPassword.getSaltedHash( userData.getPassword() ) );
		if( userData.getStatus() != null )
			user.setStatus( userData.getStatus() );
		else
			user.setStatus( UserStatus.ANDROID_SIGNUP );
		
		user = dataAccessor.createOrUpdateUser( user );
		
		UserRole userRole = dataAccessor.newUserRole();
		userRole.setUserId( user.getId() );
		userRole.setRoleId( "member" );
		dataAccessor.createOrUpdateUserRole( userRole );

		Task task = TaskQueueFactory.newTask();
		task.addParam( "userId", user.getId().toString() );
		
		TaskQueue taskQueue = TaskQueueFactory.getWelcomeUserTaskQueue();
		taskQueue.add( task );
		
		//Update Access Token Entity
		ClaymusHelper claymusHelper = ClaymusHelper.get( request );
		AccessToken accessToken = claymusHelper.createAccessToken( user.getId() );
		
		return accessToken;
	}
	
	public static UserData updateUserProfile( UserData userData, HttpServletRequest request ) 
			throws InsufficientAccessException, InvalidArgumentException{
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		User user = dataAccessor.getUser( userData.getId() );
		Logger.getLogger( UserContentHelper.class.getName() ).log( Level.INFO, "User : " + user );
		
		if( user == null )
			throw new InvalidArgumentException( "User Id is invalid" );
		
		if( userData.hasName() ){
			String fullName = userData.getName();
			user.setFirstName( fullName.substring( 0, fullName.lastIndexOf( " " ) ));
			user.setLastName( fullName.substring( fullName.lastIndexOf( " " ) + 1 ));
		}
		if( userData.hasEmail() )
			user.setEmail( userData.getEmail() );
		if( userData.hasPassword() )
			user.setPassword( userData.getPassword() );
		if( userData.hasDateOfBirth() )
			user.setDateOfBirth( userData.getDateOfBirth() );
		if( userData.hasGender() )
			user.setGender( userData.getGender() );
		
		user = dataAccessor.createOrUpdateUser( user );
		
		
		return createUserData( user );
	}
	
}

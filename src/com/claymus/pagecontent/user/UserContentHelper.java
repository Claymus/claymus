package com.claymus.pagecontent.user;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.shared.UserStatus;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.data.transfer.shared.UserData;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.user.gae.UserContentEntity;
import com.claymus.pagecontent.user.shared.UserContentData;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.claymus.taskqueue.TaskQueueFactory;

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
		
		String firstName = userData.getName().trim().substring( 0, userData.getName().lastIndexOf( " " ));
		String lastName = userData.getName().trim().substring( userData.getName().lastIndexOf( " " ) + 1 );
		
		user.setFirstName( firstName );
		user.setLastName( lastName );
		if( userData.getPassword() != null )
			user.setPassword( EncryptPassword.getSaltedHash( userData.getPassword() ) );
		if( userData.getStatus() != null )
			user.setStatus( userData.getStatus() );
		else
			user.setStatus( UserStatus.POSTLAUNCH_SIGNUP );
		
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
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		accessToken = claymusHelper.updateAccessToken( accessToken.getId(), user.getId(), new Date(), null, null );
		
		return accessToken;
	}
	
}

package com.claymus.service.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.server.ValidateFbAccessToken;
import com.claymus.commons.shared.UserStatus;
import com.claymus.commons.shared.exception.IllegalArgumentException;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.email.EmailUtil;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.claymus.pagecontent.blogpost.BlogPostContentHelper;
import com.claymus.pagecontent.pages.PagesContentHelper;
import com.claymus.pagecontent.roleaccess.RoleAccessContentHelper;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.shared.FacebookLoginUserRequest;
import com.claymus.service.shared.FacebookLoginUserResponse;
import com.claymus.service.shared.GetBlogListRequest;
import com.claymus.service.shared.GetBlogListResponse;
import com.claymus.service.shared.GetPageListRequest;
import com.claymus.service.shared.GetPageListResponse;
import com.claymus.service.shared.InviteUserRequest;
import com.claymus.service.shared.InviteUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.claymus.service.shared.SavePageContentRequest;
import com.claymus.service.shared.SavePageContentResponse;
import com.claymus.service.shared.SavePageRequest;
import com.claymus.service.shared.SavePageResponse;
import com.claymus.service.shared.SaveRoleAccessRequest;
import com.claymus.service.shared.SaveRoleAccessResponse;
import com.claymus.service.shared.SendQueryRequest;
import com.claymus.service.shared.SendQueryResponse;
import com.claymus.service.shared.UpdateUserPasswordRequest;
import com.claymus.service.shared.UpdateUserPasswordResponse;
import com.claymus.service.shared.data.FacebookLoginData;
import com.claymus.service.shared.data.PageContentData;
import com.claymus.service.shared.data.PageData;
import com.claymus.service.shared.data.UserData;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.claymus.taskqueue.TaskQueueFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class ClaymusServiceImpl extends RemoteServiceServlet
		implements ClaymusService {

	private static final Logger logger = 
			Logger.getLogger( ClaymusServiceImpl.class.getName() );

	
	@Override
	public InviteUserResponse inviteUser( InviteUserRequest request )
			throws IllegalArgumentException {
		
		UserData userData = request.getUser();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		User user = dataAccessor.getUserByEmail( userData.getEmail().toLowerCase() );

		if( user == null ) {
			user = dataAccessor.newUser();
			user.setEmail( userData.getEmail().toLowerCase() );

			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );
		
		} else if( user.getStatus() == UserStatus.PRELAUNCH_REFERRAL) {
			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );
			
		} else if( userData.getStatus() == UserStatus.PRELAUNCH_SIGNUP ) {
			throw new IllegalArgumentException( "User registered already !" );
			
		} else if( userData.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );
			
		} else if( userData.getStatus() == UserStatus.POSTLAUNCH_SIGNUP ) {
			throw new IllegalArgumentException( "User registered alread !" );
	
		} else {
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "Invitation failed ! "
					+ "Kindly contact the administrator." );
		}
			
		user.setFirstName( userData.getFirstName() );
		user.setLastName( userData.getLastName() );
		user.setStatus( UserStatus.POSTLAUNCH_REFERRAL );

		user = dataAccessor.createOrUpdateUser( user );
		
		Task task = TaskQueueFactory.newTask();
		task.addParam( "userId", user.getId().toString() );
		
		TaskQueue taskQueue = TaskQueueFactory.getInviteUserTaskQueue();
		taskQueue.add( task );
		
		return new InviteUserResponse();
	}
	
	@Override
	public RegisterUserResponse registerUser( RegisterUserRequest request )
			throws IllegalArgumentException {

		UserData userData = request.getUser();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		User user = dataAccessor.getUserByEmail( userData.getEmail().toLowerCase() );

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

		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP ) {
			throw new IllegalArgumentException( "This email id is already registered !" );

		} else {
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "User registration failed ! "
					+ "Kindly contact the administrator." );
		}
		
		user.setFirstName( userData.getFirstName() );
		user.setLastName( userData.getLastName() );
		user.setPassword( EncryptPassword.getSaltedHash( userData.getPassword() ) );
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
		
		this.getThreadLocalRequest().getSession().setAttribute(
				ClaymusHelper.SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
		
		String message = "SignUp successful! ";
		
		return new RegisterUserResponse( message );
	}

	@Override
	public LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException {

		ClaymusHelper claymusHelper =
				ClaymusHelper.get( this.getThreadLocalRequest() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		User user = dataAccessor.getUserByEmail( request.getLoginId().toLowerCase() );
		
		if( user != null && user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN ) 
			throw new IllegalArgumentException(
					"You used social media account to login. Please click "
					+ "<a href='" + claymusHelper.createForgotPasswordURL() + "' class='alert-link'>here</a>"
					+ " to generate your password or kindly use social login to login again." );
		
		if( user == null
				|| user.getStatus() == UserStatus.PRELAUNCH_REFERRAL
				|| user.getStatus() == UserStatus.PRELAUNCH_SIGNUP
				|| user.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			throw new IllegalArgumentException(
					"This email id is not yet registered. Kindly "
					+ "<a href='" + claymusHelper.createRegisterURL() + "' class='alert-link'>register</a>"
					+ " or try again with a different email id." );
			
		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP ) {

		} else {
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "Login failed ! "
					+ "Kindly contact the administrator." );
		}

		if( ! EncryptPassword.check( request.getPassword(), user.getPassword() ) )
			throw new IllegalArgumentException( "Incorrect password !" );

		this.getThreadLocalRequest().getSession().setAttribute(
				ClaymusHelper.SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
		
		return new LoginUserResponse();
	}

	@Override
	public void logoutUser() {
		this.getThreadLocalRequest().getSession().invalidate();
	}

	@Override
	public ResetUserPasswordResponse resetUserPassword(
			ResetUserPasswordRequest request ) throws IllegalArgumentException {
		
		ClaymusHelper claymusHelper =
				ClaymusHelper.get( this.getThreadLocalRequest() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		User user = dataAccessor.getUserByEmail( request.getUserEmail().toLowerCase() );
		
		if( user == null
				|| user.getStatus() == UserStatus.PRELAUNCH_REFERRAL
				|| user.getStatus() == UserStatus.PRELAUNCH_SIGNUP
				|| user.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			throw new IllegalArgumentException(
					"This email id is not yet registered. Kindly "
					+ "<a href='" + claymusHelper.createRegisterURL() + "' class='alert-link'>register</a>"
					+ " or try again with a different email id." );
			
		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP || user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN ) {

		} else {
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "Password reset failed ! "
					+ "Kindly contact the administrator." );
		}

		Task task = TaskQueueFactory.newTask();
		task.addParam( "userId", user.getId().toString() );
		
		TaskQueue taskQueue = TaskQueueFactory.getResetPasswordTaskQueue();
		taskQueue.add( task );
		
		String message = "<Strong>Reset password link generated successfully. </strong>"
							+ "Please check your email for the link.";

		return new ResetUserPasswordResponse( message );
	}
	
	@Override
	public UpdateUserPasswordResponse updateUserPassword(
			UpdateUserPasswordRequest request ) throws IllegalArgumentException {

		ClaymusHelper claymusHelper =
				ClaymusHelper.get( this.getThreadLocalRequest() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		
		String userEmail = request.getUserEmail();
		User user = null;
		
		// Request from user profile
		if( userEmail == null ) {
			
			if( ! claymusHelper.isUserLoggedIn() )
				throw new IllegalArgumentException(
						"You are not logged in. Please "
						+ "<a href='" + claymusHelper.createLoginURL() + "' class='alert-link'>login</a> and try again. "
						+ "If you have forgotten your password, you can reset your password "
						+ "<a href='" + claymusHelper.createForgotPasswordURL() + " ' class='alert-link'>here</a>." );

			user = claymusHelper.getCurrentUser();

			if( request.getCurrentPassword() == null
					||  ! EncryptPassword.check( request.getCurrentPassword(),  user.getPassword() ) )
				throw new IllegalArgumentException( "Current password is not correct. Please try again." );


		// Request via password reset link
		} else {
			
			user = dataAccessor.getUserByEmail( userEmail );
			
			if( request.getToken() == null
					|| ! user.getPassword().equals( request.getToken() ) )
				throw new IllegalArgumentException(
						"URL used is invalid or expired. "
						+ "Please check the URL and try again." );
			
			if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN )
				user.setStatus( UserStatus.POSTLAUNCH_SIGNUP );
		}
		
		user.setPassword( EncryptPassword.getSaltedHash( request.getNewPassword() ));
		dataAccessor.createOrUpdateUser( user );
		
		String message = "<strong>Password changed successfully</strong>";
		
		return new UpdateUserPasswordResponse( message );
	}

	@Override
	public SendQueryResponse sendQuery( final SendQueryRequest request )
			throws UnexpectedServerException {
		
		EmailTemplate emailTemplate = new EmailTemplate() {

			@Override
			public String getId() {
				return null;
			}

			@Override
			public void setId( String id ) {}

			@Override
			public String getSenderName() {
				return "Query Form";
			}

			@Override
			public void setSenderName( String senderName ) {}

			@Override
			public String getSenderEmail() {
				return ClaymusHelper.getSystemProperty( "email.noreply" );
			}

			@Override
			public void setSenderEmail( String senderEmail ) {}

			@Override
			public String getReplyToName() {
				return request.getName();
			}

			@Override
			public void setReplyToName( String replyToName ) {}

			@Override
			public String getReplyToEmail() {
				return request.getEmail();
			}

			@Override
			public void setReplyToEmail( String replyToEmail ) {}

			@Override
			public String getSubject() {
				return "Query from " + request.getName();
			}

			@Override
			public void setSubject( String subject ) {}

			@Override
			public String getBody() {
				return request.getQuery();
			}

			@Override
			public void setBody( String body ) {}
			
		};
		
		try {
			EmailUtil.sendMail(
					null, ClaymusHelper.getSystemProperty( "email.contact" ),
					emailTemplate, null );
			return new SendQueryResponse( "Query submitted successfully !" );
			
		} catch ( MessagingException | IOException | TemplateException e ) {
			logger.log( Level.SEVERE, "Failed to e-mail contact query.", e );
			throw new UnexpectedServerException();
		}
		
	}

	
	@Override
	public FacebookLoginUserResponse facebookLogin(
			FacebookLoginUserRequest request)
			throws IllegalArgumentException {
		
		FacebookLoginData fbLoginUserData = request.getFacebookLoginData();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		User user = null;
		
		if( fbLoginUserData.getEmail() != null )
			user = dataAccessor.getUserByEmail( fbLoginUserData.getEmail() );
		else 
			throw new IllegalArgumentException( "Your Email address is required to complete basic login activities. Please provide provide access to your facebook email address" );
		
		ValidateFbAccessToken validateToken = new ValidateFbAccessToken( fbLoginUserData.getAccessToken() );
		try {
			if( validateToken.isValid() ) {
				if( user == null ) {
					user = dataAccessor.newUser();
					user.setEmail( fbLoginUserData.getEmail() );
					user.setFirstName( fbLoginUserData.getFirstName() );
					user.setLastName( fbLoginUserData.getLastName() );
					user.setCampaign( fbLoginUserData.getCampaign() );
					user.setReferer( fbLoginUserData.getReferer() );
					user.setStatus( UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN );
					user.setSignUpDate( new Date() );
					
					dataAccessor.createOrUpdateUser( user );
					//Getting new user.
					user = dataAccessor.getUserByEmail( user.getEmail() );
				}
				//Setting session for the user.
				this.getThreadLocalRequest().getSession().setAttribute(
						ClaymusHelper.SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
			}
			else
				throw new IllegalArgumentException( "Not a valid access token" );
		} catch (Exception e) {
			logger.log( Level.SEVERE, "" , e);
		} 
		
		return new FacebookLoginUserResponse();
	}

	
	@Override
	public SavePageResponse savePage( SavePageRequest request )
			throws IllegalArgumentException, InsufficientAccessException {

		PageData pageData = request.getPageData();
		String uri = pageData.getUriAlias().toLowerCase();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Page page = dataAccessor.getPage( uri );
		
		if( pageData.getId() == null ) { // Add page usecasee
			
			if( ! PagesContentHelper.hasRequestAccessToAddPageData( this.getThreadLocalRequest() ) )
				throw new InsufficientAccessException();

			if( page != null )
				throw new IllegalArgumentException( "Another page is already associated with this URL." );
			
			page = dataAccessor.newPage();
			page.setUriAlias( uri );
			page.setTitle( pageData.getTitle() );
			page.setCreationDate( new Date() );

			
		} else { // Update page usecase

			if( ! PagesContentHelper.hasRequestAccessToUpdatePageData( this.getThreadLocalRequest() ) )
				throw new InsufficientAccessException();

			if( page != null && ! page.getId().equals( pageData.getId() ) )
				throw new IllegalArgumentException( "Another page is already associated with this URL." );

			if( page == null ) {
				page = dataAccessor.getPage( pageData.getId() );
				page.setUri( uri );
			}
			
			page.setTitle( pageData.getTitle() );
		}

		page = dataAccessor.createOrUpdatePage( page );
		
		return new SavePageResponse();
	}
	
	public GetPageListResponse getPageList( GetPageListRequest request )
			throws InsufficientAccessException {
		
		if( ! PagesContentHelper.hasRequestAccessToListPageData( this.getThreadLocalRequest() ) )
			throw new InsufficientAccessException();

		DataListCursorTuple<Page> pageListCursorTuple = DataAccessorFactory
				.getDataAccessor( this.getThreadLocalRequest() )
				.getPageList( request.getCursor(), request.getResultCount() );
		
		ClaymusHelper claymusHelper = ClaymusHelper.get( this.getThreadLocalRequest() );
		
		List<PageData> pageDataList = new ArrayList<PageData>( pageListCursorTuple.getDataList().size() );
		for( Page page : pageListCursorTuple.getDataList() )
			pageDataList.add( claymusHelper.createPageData( page ) );
		
		return new GetPageListResponse( pageDataList, pageListCursorTuple.getCursor() );
	}

	/*
	 * Owner Module: PageContent
	 * API Version: 3.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SavePageContentResponse savePageContent( SavePageContentRequest request )
			throws InsufficientAccessException {
		
		PageContentData pageContentData = request.getPageContentData();
		@SuppressWarnings("rawtypes")
		PageContentHelper pageContentHelper =
				PageContentRegistry.getPageContentHelper( pageContentData.getClass() );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );

		PageContent pageContent;
		if( pageContentData.getId() == null ) { // Add PageContent usecase

			if( ! pageContentHelper.hasRequestAccessToAddContent( this.getThreadLocalRequest() ) )
				throw new InsufficientAccessException();
		
			pageContent = pageContentHelper.createOrUpdateFromData( pageContentData, null );
			pageContent.setCreationDate( new Date() );
			pageContent.setLastUpdated( new Date() );

		} else { // Update PageContent usecase

			if( ! pageContentHelper.hasRequestAccessToUpdateContent( this.getThreadLocalRequest() ) )
				throw new InsufficientAccessException();
			
			pageContent = dataAccessor.getPageContent( pageContentData.getId() );
			pageContent = pageContentHelper.createOrUpdateFromData( pageContentData, pageContent );
			pageContent.setLastUpdated( new Date() );
		}
		
		dataAccessor.createOrUpdatePageContent( pageContent );
		
		return new SavePageContentResponse( pageContent.getId() );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public GetBlogListResponse getBlogPostList( GetBlogListRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		DataListCursorTuple<BlogPostContent> dataListCursorTuple =
				dataAccessor.getBlogPostContentList(
						request.getBlogId(), request.getCursor(),
						request.getResultCount() );
		
		List<BlogPostContent> blogPostContentList = dataListCursorTuple.getDataList();
		List<String> blogPostHtmlList = new ArrayList<>( blogPostContentList.size() );
		PageContentProcessor blogPostContentProcessor =
				PageContentRegistry.getPageContentProcessor(
						BlogPostContentHelper.newBlogPostContent().getClass() );
		for( PageContent blogPostContent : blogPostContentList ) {
			((BlogPostContent) blogPostContent).setPreview( true );
			blogPostHtmlList.add(
					blogPostContentProcessor.generateHtml(
							blogPostContent, this.getThreadLocalRequest() ) );
		}
		
		return new GetBlogListResponse( blogPostHtmlList, dataListCursorTuple.getCursor() );
	}

	/*
	 * Owner Module: RoleAccess (PageContent)
	 * API Version: 3.0
	 */
	@Override
	public SaveRoleAccessResponse saveRoleAccess( SaveRoleAccessRequest request )
			throws InsufficientAccessException {
		
		if( ! RoleAccessContentHelper.hasRequestAccessToUpdateAccessData( this.getThreadLocalRequest() ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		RoleAccess roleAccess = dataAccessor.newRoleAccess();
		roleAccess.setRoleId( request.getRoleId() );
		roleAccess.setAccessId( request.getAccessId() );
		roleAccess.setAccess( request.getAccess() );
		dataAccessor.createOrUpdateRoleAccess( roleAccess );
		
		return new SaveRoleAccessResponse();
	}
	
}

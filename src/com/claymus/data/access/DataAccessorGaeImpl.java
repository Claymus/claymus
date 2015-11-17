package com.claymus.data.access;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.claymus.commons.shared.CommentFilter;
import com.claymus.commons.shared.CommentParentType;
import com.claymus.data.access.gae.AppPropertyEntity;
import com.claymus.data.access.gae.AuditLogEntity;
import com.claymus.data.access.gae.CommentEntity;
import com.claymus.data.access.gae.EmailTemplateEntity;
import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.data.access.gae.PageContentEntityStub;
import com.claymus.data.access.gae.PageEntity;
import com.claymus.data.access.gae.PageLayoutEntity;
import com.claymus.data.access.gae.RoleAccessEntity;
import com.claymus.data.access.gae.RoleEntity;
import com.claymus.data.access.gae.UserEntity;
import com.claymus.data.access.gae.UserRoleEntity;
import com.claymus.data.transfer.AppProperty;
import com.claymus.data.transfer.AuditLog;
import com.claymus.data.transfer.Comment;
import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.Role;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.claymus.pagecontent.blogpost.gae.BlogPostContentEntity;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

@SuppressWarnings("serial")
public class DataAccessorGaeImpl implements DataAccessor {

	private static final Logger logger = 
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );


	protected final PersistenceManager pm;
	
	
	public DataAccessorGaeImpl() {
		this.pm = pmfInstance.getPersistenceManager();
	}

	
	@Override
	public PersistenceManager getPersistenceManager() {
		return this.pm;
	}
	
	
	protected <T> T getEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		return pm.detachCopy( entity );
	}

	protected <T> T createOrUpdateEntity( T entity ) {
		entity = pm.makePersistent( entity );
		return pm.detachCopy( entity );
	}
	
	protected <T> List<T> createOrUpdateEntityList( List<T> entityList ) {
		entityList = (List<T>) pm.makePersistentAll( entityList );
		return (List<T>) pm.detachCopyAll( entityList );
	}
	
	protected <T> void deleteEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		pm.deletePersistent( entity );
	}


	@Override
	public AppProperty newAppProperty( String id ) {
		AppPropertyEntity appProperty = new AppPropertyEntity();
		appProperty.setId( id );
		return appProperty;
	}

	@Override
	public AppProperty getAppProperty( String id ) {
		if( id == null )
			return null;
		
		try{
			return getEntity( AppPropertyEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public AppProperty createOrUpdateAppProperty( AppProperty appProperty ) {
		return createOrUpdateEntity( appProperty );
	}


	@Override
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( Long id ) {
		return id == null ? null : getEntity( UserEntity.class, id );
	}
	
	@Override
	public User getUserByEmail( String email ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserEntity.class ) )
						.addFilter( "email", email )
						.build();
		
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.execute( email );
		return userList.size() == 0 ? null : pm.detachCopy( userList.get( 0 ) );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserEntity.class ) )
						.build();

		return (List<User>) query.execute();
	}
	
	@Override
	public User createOrUpdateUser( User user ) {
		return createOrUpdateEntity( user );
	}

	
	@Override
	public Role newRole() {
		return new RoleEntity();
	}

	@Override
	public Role getRole( Long id ) {
		return getEntity( RoleEntity.class, id );
	}

	@Override
	public Role createOrUpdateRole( Role role ) {
		return createOrUpdateEntity( role );
	}

	@Override
	public Boolean deleteUser( Long id ){
		try{
//			deleteEntity( UserEntity.class, id );
			return false;
		} catch(JDOObjectNotFoundException e ){
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public UserRole newUserRole() {
		return new UserRoleEntity();
	}

	@Override
	public List<UserRole> getUserRoleList( Long userId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserRoleEntity.class ) )
						.addFilter( "userId", userId )
						.build();
		
		@SuppressWarnings("unchecked")
		List<UserRole> userRoleList = (List<UserRole>) query.execute( userId );
		return (List<UserRole>) pm.detachCopyAll( userRoleList );
	}
	
	@Override
	public UserRole createOrUpdateUserRole( UserRole userRole ) {
		( (UserRoleEntity) userRole ).setId(
				userRole.getUserId() + "-" + userRole.getRoleId() );
		return createOrUpdateEntity( userRole );
	}

	@Override
	public Boolean deleteUserRole( String id ){
		try {
//			deleteEntity( UserRoleEntity.class, id );
			return false;
		} catch( JDOObjectNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public RoleAccess newRoleAccess() {
		return new RoleAccessEntity();
	}

	@Override
	public RoleAccess getRoleAccess( String roleId, String accessId ) {
		try {
			return getEntity( RoleAccessEntity.class, roleId + "-"  + accessId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public RoleAccess createOrUpdateRoleAccess( RoleAccess roleAccess ) {
		( (RoleAccessEntity) roleAccess ).setId(
				roleAccess.getRoleId() + "-" + roleAccess.getAccessId() );
		return createOrUpdateEntity( roleAccess );
	}

	
	@Override
	public Page newPage() {
		return new PageEntity();
	}
	
	@Override
	public Page getPage( Long id ) {
		return getEntity( PageEntity.class, id );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getPage( String uri ) {
		Query query = new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "uriAlias", uri )
						.addOrdering( "creationDate", true )
						.build();

		List<Page> pageList = (List<Page>) query.execute( uri );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for uri " + uri );
		
		if( pageList.size() != 0 )
			return pm.detachCopy( pageList.get( 0 ) );

		
		query = new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "uri", uri )
						.addOrdering( "creationDate", true )
						.build();

		pageList = (List<Page>) query.execute( uri );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for uri alias " + uri );

		return pageList.size() == 0 ? null : pm.detachCopy( pageList.get( 0 ) );
	}
	
	@Override
	public Page getPage( String pageType, Long primaryContentId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "type", pageType )
						.addFilter( "primaryContentId", primaryContentId )
						.addOrdering( "creationDate", true )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Page> pageList = (List<Page>) query.execute( pageType, primaryContentId );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for PageType " + pageType + ", PageContent " + primaryContentId );
		return pageList.size() == 0 ? null : pm.detachCopy( pageList.get( 0 ) );
	}
	
	@Override
	public DataListCursorTuple<Page> getPageList( String cursorStr, Integer resultCount ) {
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addOrdering( "creationDate", false );
		
		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );
			
		Query query = gaeQueryBuilder.build();
		
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<Page> pageList = (List<Page>) query.execute();
		Cursor cursor = JDOCursorHelper.getCursor( pageList );

		return new DataListCursorTuple<Page>(
				(List<Page>) pm.detachCopyAll( pageList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public Page createOrUpdatePage( Page page ) {
		return createOrUpdateEntity( page );
	}

	@Override
	public Boolean deletePage( Long id ){
		try {
//			deleteEntity( PageEntity.class, id );
			return false;
		} catch( JDOObjectNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public PageContent getPageContent( Long id ) {
		PageContentEntityStub stub = getEntity( PageContentEntityStub.class, id );
		try {
			return (PageContent) getEntity( Class.forName( stub.getType() ), id );
		} catch( ClassNotFoundException e ) {
			logger.log( Level.SEVERE, "PageContentEntity Type not found !", e );
			return null;
		}
	}
	
	@Override
	public List<PageContent> getPageContentList( Long pageId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PageContentEntityStub.class ) )
						.addFilter( "pageId", pageId )
						.build();

		@SuppressWarnings("unchecked")
		List<PageContentEntityStub> stubList = (List<PageContentEntityStub>) query.execute( pageId );
		
		List<PageContent> pageContentList = new ArrayList<>( stubList.size() );
		for( PageContentEntityStub stub : stubList ) {
			try {
				pageContentList.add( (PageContent) getEntity( Class.forName( stub.getType() ), stub.getId() ) );
			} catch( ClassNotFoundException e ) {
				logger.log( Level.SEVERE, "PageContentEntity Type not found !", e );
			}
		}
		
		return pageContentList;
	}

	@Override
	public DataListCursorTuple<BlogPostContent> getBlogPostContentList(
			Long blogId, String cursorStr, int resultCount ) {
		
		Query query =
				new GaeQueryBuilder( pm.newQuery( BlogPostContentEntity.class ) )
						.addFilter( "blogId", blogId )
						.addOrdering( "creationDate", false )
						.setRange( 0, resultCount )
						.build();

		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<BlogPostContent> blogPostContentList = (List<BlogPostContent>) query.execute( blogId );
		Cursor cursor = JDOCursorHelper.getCursor( blogPostContentList );

		return new DataListCursorTuple<BlogPostContent>(
				(List<BlogPostContent>) pm.detachCopyAll( blogPostContentList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	@Override
	public PageContent createOrUpdatePageContent( PageContent pageContent ) {
		return createOrUpdateEntity( pageContent );
	}

	@Override
	public Boolean deletePageContent( Long id ){
		try {
//			deleteEntity( PageContentEntity.class, id );
			return false;
		} catch( JDOObjectNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public PageLayout newPageLayout() {
		return new PageLayoutEntity();
	}

	@Override
	public PageLayout getPageLayout( Long id ) {
		return getEntity( PageLayoutEntity.class, id );
	}

	@Override
	public PageLayout createOrUpdatePageLayout( PageLayout pageLayout ) {
		return createOrUpdateEntity( pageLayout );
	}

	
	@Override
	public EmailTemplate newEmailTemplate() {
		return new EmailTemplateEntity();
	}


	@Override
	public Comment newComment() {
		return new CommentEntity();
	}
	
	@Override
	public Comment getCommentById( Long id ){
		if( id == null )
			return null;
		
		try{
			return getEntity( CommentEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	public List<Comment> getCommentList( String parentId , CommentParentType parentType, Long userId ){
		Query query =
				new GaeQueryBuilder( pm.newQuery( CommentEntity.class ) )
					.addFilter( "parentId", parentId)
					.addFilter( "parentType", parentType)
					.addFilter( "userId", userId )
					.addOrdering( "commentLastUpdatedDate", false )
					.build();
		
		@SuppressWarnings("unchecked")
		List<Comment> commnetList = (List<Comment>) query.execute( parentId, parentType, userId );
		
		return (List<Comment>) pm.detachCopyAll( commnetList );
		 
	}
	
	@Override
	public DataListCursorTuple<Comment> getCommentList(
			CommentFilter commentFilter, String cursorStr, Integer resultCount ) {
		
		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( CommentEntity.class ) );
		
		if( commentFilter.getParentId() != null )
			gaeQueryBuilder.addFilter( "parentId", commentFilter.getParentId() );
		if( commentFilter.getParentType() != null )
			gaeQueryBuilder.addFilter( "parentType", commentFilter.getParentType() );
		if( commentFilter.getUserId() != null )
			gaeQueryBuilder.addFilter( "userId", commentFilter.getUserId() );
		
		gaeQueryBuilder.addOrdering( "commentLastUpdatedDate", false );
		
		Query query = gaeQueryBuilder.build();
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount ); 
		
		@SuppressWarnings("unchecked")
		List<Comment> commentList = 
							(List<Comment>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( commentList );

		return new DataListCursorTuple<Comment>(
				(List<Comment>) pm.detachCopyAll( commentList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public Comment createOrUpdateComment( Comment comment ) {
		return createOrUpdateEntity( comment );
	}

	@Override
	public Boolean deleteComment( Long id ){
		try {
//			deleteEntity( CommentEntity.class, id );
			return false;
		} catch( JDOObjectNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public AccessToken newAccessToken() {
		return new AccessTokenEntity();
	}

	@Override
	public AccessToken getAccessToken( String accessTokenId ) {
		if( accessTokenId == null )
			return null;
		
		try{
			AccessToken accessToken = getEntity( AccessTokenEntity.class, accessTokenId );
			return accessToken.getExpiry().before( new Date() ) ? null : accessToken;
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public AccessToken getAccessTokenById(String accessTokenId) {
		if( accessTokenId == null )
			return null;
		
		try{
			return getEntity( AccessTokenEntity.class, accessTokenId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public AccessToken createAccessToken( AccessToken accessToken ) {
		accessToken.setCreationDate( new Date() );
		if( accessToken.getExpiry() == null )
			accessToken.setExpiry( new Date( new Date().getTime() + 3600000 ) ); // 1Hr
		accessToken.setLastUpdatedDate( new Date() );
		Transaction tx = null;
		while( true ) {
			((AccessTokenEntity) accessToken).setId( UUID.randomUUID().toString() );
			try {
				tx = pm.currentTransaction();
				tx.begin();
				pm.getObjectById( AccessTokenEntity.class, accessToken.getId() );
			} catch( JDOObjectNotFoundException e ) {
				try{
					AccessToken at = pm.makePersistent( accessToken );
					tx.commit();
					return pm.detachCopy( at );
				} catch( JDODataStoreException ex ) {
					logger.log( Level.INFO, "Transaction failed. Retrying ...", ex );
				}
			} finally {
				if( tx.isActive() )
					tx.rollback();
			}
		}
	}
	
	@Override
	public AccessToken updateAccessToken( AccessToken accessToken ) {
		return createOrUpdateEntity( accessToken );
	}

	
	@Override
	public AuditLog newAuditLog() {
		return new AuditLogEntity();
	}

	@Override
	public AuditLog createAuditLog( AuditLog auditLog ) {
		( (AuditLogEntity) auditLog ).setCreationDate( new Date() );
		return createOrUpdateEntity( auditLog );
	}

	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String cursorStr, Integer resultCount ) {
		
		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( AuditLogEntity.class ) )
						.addOrdering( "creationDate", false );
		
		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );
		
		Query query = gaeQueryBuilder.build();

		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions( extensionMap );
		}
		
		@SuppressWarnings("unchecked")
		List<AuditLog> audtiLogList = (List<AuditLog>) query.execute();
		Cursor cursor = JDOCursorHelper.getCursor( audtiLogList );
		
		return new DataListCursorTuple<>( 
				(List<AuditLog>) pm.detachCopyAll( audtiLogList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	
	@Override
	public void destroy() {
		pm.close();
	}


}

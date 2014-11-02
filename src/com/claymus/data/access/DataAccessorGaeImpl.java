package com.claymus.data.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.claymus.data.access.gae.EmailTemplateEntity;
import com.claymus.data.access.gae.PageContentEntityStub;
import com.claymus.data.access.gae.PageEntity;
import com.claymus.data.access.gae.PageLayoutEntity;
import com.claymus.data.access.gae.RoleAccessEntity;
import com.claymus.data.access.gae.RoleEntity;
import com.claymus.data.access.gae.UserEntity;
import com.claymus.data.access.gae.UserRoleEntity;
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
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( Long id ) {
		return getEntity( UserEntity.class, id );
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
	public DataListCursorTuple<Page> getPageList( String cursorStr, int resultCount ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
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
	public void destroy() {
		pm.close();
	}

}

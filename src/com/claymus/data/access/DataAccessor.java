package com.claymus.data.access;

import java.io.Serializable;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.claymus.commons.shared.CommentFilter;
import com.claymus.commons.shared.CommentParentType;
import com.claymus.data.transfer.AccessToken;
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

public interface DataAccessor extends Serializable {

	@Deprecated
	PersistenceManager getPersistenceManager();

	
	AppProperty newAppProperty( String id );

	AppProperty getAppProperty( String id );
	
	AppProperty createOrUpdateAppProperty( AppProperty appProperty );


	User newUser();

	User getUser( Long id );

	User getUserByEmail( String email );
	
	List<User> getUserList();

	User createOrUpdateUser( User user );

	
	Role newRole();

	Role getRole( Long id );

	Role createOrUpdateRole( Role role );

	
	UserRole newUserRole();
	
	List<UserRole> getUserRoleList( Long userId );

	UserRole createOrUpdateUserRole( UserRole userRole );

	
	RoleAccess newRoleAccess();
	
	RoleAccess getRoleAccess( String roleId, String accessId );
	
	RoleAccess createOrUpdateRoleAccess( RoleAccess roleAccess );

	
	Page newPage();
	
	Page getPage( Long id );
	
	Page getPage( String uri );

	Page getPage( String pageType, Long primaryContentId );

	DataListCursorTuple<Page> getPageList( String cursor, Integer resultCount );

	Page createOrUpdatePage( Page page );
	

	PageContent getPageContent( Long id );

	List<PageContent> getPageContentList( Long pageId );

	DataListCursorTuple<BlogPostContent> getBlogPostContentList( Long blogId, String cursor, int resultCount );

	PageContent createOrUpdatePageContent( PageContent pageContent );
	
	
	PageLayout newPageLayout();
	
	PageLayout getPageLayout( Long id );
	
	PageLayout createOrUpdatePageLayout( PageLayout pageLayout );

	
	EmailTemplate newEmailTemplate();
	
	
	Comment newComment();
	
	Comment getCommentById( Long id );
	
	List<Comment> getCommentList( String parentId , CommentParentType parentType, Long userId );
	
	DataListCursorTuple<Comment> getCommentList( CommentFilter commentFilter , String cursor, Integer resultCount );
	
	Comment createOrUpdateComment( Comment comment );
	
	
	AccessToken newAccessToken();

	AccessToken getAccessToken( String accessTokenId );
	
	AccessToken getAccessTokenById( String accessTokenId );
	
	AccessToken createAccessToken( AccessToken accessToken );

	AccessToken updateAccessToken( AccessToken accessToken );


	AuditLog newAuditLog();
	
	AuditLog createAuditLog( AuditLog auditLog );
	
	DataListCursorTuple<AuditLog> getAuditLogList( String cursor, Integer resultCount );

	
	void destroy();
	
}

package com.claymus.data.access;

import java.io.Serializable;
import java.util.List;

import javax.jdo.PersistenceManager;

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

	DataListCursorTuple<Page> getPageList( String cursorStr, int resultCount );

	Page createOrUpdatePage( Page page );
	

	PageContent getPageContent( Long id );

	List<PageContent> getPageContentList( Long pageId );

	DataListCursorTuple<BlogPostContent> getBlogPostContentList( Long blogId, String cursorStr, int resultCount );

	PageContent createOrUpdatePageContent( PageContent pageContent );
	
	
	PageLayout newPageLayout();
	
	PageLayout getPageLayout( Long id );
	
	PageLayout createOrUpdatePageLayout( PageLayout pageLayout );

	
	EmailTemplate newEmailTemplate();
	
	
	void destroy();
	
}

package com.claymus.service.client;

import com.claymus.commons.shared.exception.IllegalArgumentException;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../service.claymus")
public interface ClaymusService extends RemoteService {
	
	InviteUserResponse inviteUser( InviteUserRequest request )
			throws IllegalArgumentException;
	
	RegisterUserResponse registerUser( RegisterUserRequest request )
			throws IllegalArgumentException;

	LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException;
	
	FacebookLoginUserResponse facebookLogin( FacebookLoginUserRequest request )
			throws IllegalArgumentException;
	
	void logoutUser();
	
	ResetUserPasswordResponse resetUserPassword( ResetUserPasswordRequest request )
			throws IllegalArgumentException;
	
	UpdateUserPasswordResponse updateUserPassword( UpdateUserPasswordRequest request )
			throws IllegalArgumentException;
	
	SendQueryResponse sendQuery( SendQueryRequest request )
			throws UnexpectedServerException;

	
	// API Version: 4.0; Owner Module: PagesContent;
	SavePageResponse savePage( SavePageRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	// API Version: 4.0; Owner Module: PagesContent;
	GetPageListResponse getPageList( GetPageListRequest request )
			throws InsufficientAccessException;

	SavePageContentResponse savePageContent( SavePageContentRequest request )
			throws InsufficientAccessException;

	// API Version: 4.0; Owner Module: BlogContent;
	GetBlogListResponse getBlogPostList( GetBlogListRequest request )
			throws UnexpectedServerException, InsufficientAccessException;

	SaveRoleAccessResponse saveRoleAccess( SaveRoleAccessRequest request )
			throws InsufficientAccessException;
	
}
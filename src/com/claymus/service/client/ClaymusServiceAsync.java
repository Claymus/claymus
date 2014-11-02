package com.claymus.service.client;

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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClaymusServiceAsync {
	
	void inviteUser(
			InviteUserRequest request,
			AsyncCallback<InviteUserResponse> callback );
	
	void registerUser(
			RegisterUserRequest request,
			AsyncCallback<RegisterUserResponse> callback );

	void loginUser(
			LoginUserRequest request,
			AsyncCallback<LoginUserResponse> callback );
	
	void facebookLogin(
			FacebookLoginUserRequest request,
			AsyncCallback<FacebookLoginUserResponse> callback );
	
	void logoutUser( AsyncCallback<Void> callback );
	
	void resetUserPassword(
			ResetUserPasswordRequest request,
			AsyncCallback<ResetUserPasswordResponse> callback );
	
	void updateUserPassword(
			UpdateUserPasswordRequest request,
			AsyncCallback<UpdateUserPasswordResponse> callback );

	void sendQuery(
			SendQueryRequest request,
			AsyncCallback<SendQueryResponse> callback );

	
	void savePage(
			SavePageRequest request,
			AsyncCallback<SavePageResponse> callback );

	void getPageList(
			GetPageListRequest request,
			AsyncCallback<GetPageListResponse> callback );

	void savePageContent(
			SavePageContentRequest request,
			AsyncCallback<SavePageContentResponse> callback );

	void getBlogPostList( 
			GetBlogListRequest request,
			AsyncCallback<GetBlogListResponse> callback );

	void saveRoleAccess(
			SaveRoleAccessRequest request,
			AsyncCallback<SaveRoleAccessResponse> callback );
	
}
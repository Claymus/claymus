package com.claymus.pagecontent.audit;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.UserAccessHelper;
import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.AuditLog;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.client.AccessTokenData;
import com.claymus.data.transfer.client.AuditLogData;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.audit.gae.AuditContentEntity;
import com.claymus.pagecontent.audit.shared.AuditContentData;
import com.claymus.service.shared.data.UserData;

public class AuditContentHelper extends PageContentHelper<
		AuditContent,
		AuditContentData,
		AuditContentProcessor> {
	
	public static final Access ACCESS_TO_LIST_AUDIT_DATA =
			new Access( "audit_data_list", false, "List Audit Logs" );
	
	
	@Override
	public String getModuleName() {
		return "Audit";
	}

	@Override
	public Double getModuleVersion() {
		return 5.1;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_AUDIT_DATA
		};
	}
	
	
	public static AuditContent newAuditContent() {
		return new AuditContentEntity();
	}
	
	
	public static boolean hasRequestAccessToListAuditData( HttpServletRequest request ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) )
			return UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_LIST_AUDIT_DATA, request );
		return false;
	}
	
	
	private static AuditLogData createAuditLogData( AuditLog auditLog, AccessToken accessToken, User user ) {
		if( auditLog == null )
			return null;
		
		AuditLogData auditLogData = new AuditLogData();
		
		auditLogData.setId( auditLog.getId() );
		auditLogData.setEventId( auditLog.getEventId() );
		auditLogData.setEventDataOld( auditLog.getEventDataOld() );
		auditLogData.setEventDataNew( auditLog.getEventDataNew() );
		auditLogData.setAccessToken( createAccessTokenData( accessToken, user ) );
		auditLogData.setCreationDate( auditLog.getCreationDate() );
		
		return auditLogData;
	}

	private static AccessTokenData createAccessTokenData( AccessToken accessToken, User user ) {
		if( accessToken == null )
			return null;
		
		AccessTokenData accessTokenData = new AccessTokenData();
		
		accessTokenData.setId( accessToken.getId() );
		accessTokenData.setType( accessToken.getType() );
		accessTokenData.setUserId( accessToken.getUserId() );
		accessTokenData.setUser( createUserData( user ) );
		accessTokenData.setCreationDate( accessToken.getCreationDate() );
		
		return accessTokenData;
	}

	private static UserData createUserData( User user ) {
		if( user == null )
			return null;
		
		UserData userData = new UserData();
		
		userData.setFirstName( user.getFirstName() );
		userData.setLastName( user.getLastName() );
		userData.setEmail( user.getEmail() );
		
		return userData;
	}
	
	
	public static DataListCursorTuple<AuditLogData> getAuditLogList( String cursor, Integer resultCount, HttpServletRequest request )
			throws InsufficientAccessException {
		
		if( ! hasRequestAccessToListAuditData( request ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<AuditLog> auditLogListCursorTuple = 
				dataAccessor.getAuditLogList( cursor, resultCount );
		List<AuditLogData> auditLogDataList = new ArrayList<AuditLogData>();
		for( AuditLog auditLog : auditLogListCursorTuple.getDataList() ) {
			AccessToken accessToken = dataAccessor.getAccessTokenById( auditLog.getAccessId() );
			User user = accessToken == null ? null : dataAccessor.getUser( accessToken.getUserId() );
			auditLogDataList.add( createAuditLogData( auditLog, accessToken, user ));
		}
		
		return new DataListCursorTuple<AuditLogData>( auditLogDataList, auditLogListCursorTuple.getCursor() );
	}

}

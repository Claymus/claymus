package com.claymus.pagecontent.audit.api;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.client.AuditLogData;
import com.claymus.pagecontent.audit.AuditContentHelper;
import com.claymus.pagecontent.audit.api.shared.GetAuditLogListRequest;
import com.claymus.pagecontent.audit.api.shared.GetAuditLogListResponse;

@SuppressWarnings("serial")
@Bind( uri = "/audit/log/list" )
public class AuditLogListApi extends GenericApi {
	
	private Logger logger = Logger.getLogger( AuditLogListApi.class.getName() );

	
	@Get
	public GetAuditLogListResponse getAuditLogContent( GetAuditLogListRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		
		String cursor = request.getCursor();
		int pageSize = request.getResultCount();
		
		logger.log( Level.SEVERE, "CURSOR / PAGESIZE : " + cursor + "/" + pageSize );
		
		DataListCursorTuple<AuditLogData> auditLogListCursorTuple = 
				AuditContentHelper.getAuditLogList( cursor, pageSize, this.getThreadLocalRequest() );
		
		logger.log( Level.SEVERE, "AUDIT LOG LIST : " + auditLogListCursorTuple.getDataList().size() );
		
		Map<String, String> userEmailList = new HashMap<>();
		for( AuditLogData auditLogData : auditLogListCursorTuple.getDataList() ){
			AccessToken accessToken = dataAccessor.getAccessTokenById( auditLogData.getAccessToken().getId() );
			User user = dataAccessor.getUser( accessToken.getUserId() );
			if( user != null )
				userEmailList.put( auditLogData.getId().toString(), user.getEmail() );
			else
				userEmailList.put( auditLogData.getId().toString(), "" );
		}
		
		cursor = auditLogListCursorTuple.getCursor();
		
		return new GetAuditLogListResponse( auditLogListCursorTuple.getDataList(), userEmailList, cursor );

	}
}

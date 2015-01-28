package com.claymus.pagecontent.audit;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.audit.shared.AuditLogData;
import com.claymus.pagecontent.audit.shared.GetAuditLogContentRequest;
import com.claymus.pagecontent.audit.shared.GetAuditLogContentResponse;

@SuppressWarnings("serial")
@Bind( uri = "/auditlog" )
public class AuditLogContentApi extends GenericApi {
	
	private Logger logger = Logger.getLogger( AuditLogContentApi.class.getName() );

	@Get
	public GetAuditLogContentResponse getAuditLogContent( GetAuditLogContentRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		
		String cursor = request.getCursor();
		int pageSize = request.getResultCount();
		
		logger.log( Level.SEVERE, "CURSOR / PAGESIZE : " + cursor + "/" + pageSize );
		
		DataListCursorTuple<AuditLogData> auditLogListCursorTuple = 
				AuditLogContentHelper.getAuditLog( cursor, pageSize, this.getThreadLocalRequest() );
		
		logger.log( Level.SEVERE, "AUDIT LOG LIST : " + auditLogListCursorTuple.getDataList().size() );
		
		Map<String, String> userEmailList = new HashMap<>();
		for( AuditLogData auditLogData : auditLogListCursorTuple.getDataList() ){
			AccessToken accessToken = dataAccessor.getAccessTokenById( auditLogData.getAccessId() );
			User user = dataAccessor.getUser( accessToken.getUserId() );
			if( user != null )
				userEmailList.put( auditLogData.getId().toString(), user.getEmail() );
			else
				userEmailList.put( auditLogData.getId().toString(), "" );
		}
		
		cursor = auditLogListCursorTuple.getCursor();
		
		return new GetAuditLogContentResponse( auditLogListCursorTuple.getDataList(), userEmailList, cursor );

	}
}

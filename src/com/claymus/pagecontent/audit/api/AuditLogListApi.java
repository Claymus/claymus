package com.claymus.pagecontent.audit.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.client.AuditLogData;
import com.claymus.pagecontent.audit.AuditContentHelper;
import com.claymus.pagecontent.audit.api.shared.GetAuditLogListRequest;
import com.claymus.pagecontent.audit.api.shared.GetAuditLogListResponse;

@SuppressWarnings("serial")
@Bind( uri = "/audit/log/list" )
public class AuditLogListApi extends GenericApi {
	
	@Get
	public GetAuditLogListResponse getAuditLogContent( GetAuditLogListRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		DataListCursorTuple<AuditLogData> auditLogListCursorTuple = 
				AuditContentHelper.getAuditLogList( request.getCursor(), request.getResultCount(), this.getThreadLocalRequest() );
		
		return new GetAuditLogListResponse( auditLogListCursorTuple.getDataList(), auditLogListCursorTuple.getCursor() );
	}
	
}

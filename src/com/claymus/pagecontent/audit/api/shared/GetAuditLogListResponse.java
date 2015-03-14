package com.claymus.pagecontent.audit.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.claymus.data.transfer.shared.AuditLogData;

@SuppressWarnings("serial")
public class GetAuditLogListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<AuditLogData> auditLogList;
	
	@SuppressWarnings("unused")
	private String cursor;
	
	
	@SuppressWarnings("unused")
	private GetAuditLogListResponse() {}
	
	public GetAuditLogListResponse( List<AuditLogData> auditLogList, String cursor ) {
		this.auditLogList = auditLogList;
		this.cursor = cursor;
	}

}

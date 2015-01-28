package com.claymus.pagecontent.audit.api.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.claymus.api.shared.GenericResponse;
import com.claymus.data.transfer.client.AuditLogData;

@SuppressWarnings("serial")
public class GetAuditLogListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<AuditLogData> auditLogDataList;
	
	@SuppressWarnings("unused")
	private Map<String, String> userEmailList = new HashMap<>();
	
	@SuppressWarnings("unused")
	private String cursor;
	
	
	public GetAuditLogListResponse() {}
	
	public GetAuditLogListResponse( List<AuditLogData> auditLogDataList,
				Map<String, String> userEmailList,
				String cursor ){
		this.auditLogDataList = auditLogDataList;
		this.userEmailList = userEmailList;
		this.cursor = cursor;
	}

}

package com.claymus.pagecontent.audit.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetAuditLogContentResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<AuditLogData> auditLogDataList;
	
	@SuppressWarnings("unused")
	private Map<String, String> userEmailList = new HashMap<>();
	
	@SuppressWarnings("unused")
	private String cursor;
	
	public GetAuditLogContentResponse() {}
	
	public GetAuditLogContentResponse( List<AuditLogData> auditLogDataList,
				Map<String, String> userEmailList,
				String cursor ){
		this.auditLogDataList = auditLogDataList;
		this.userEmailList = userEmailList;
		this.cursor = cursor;
	}

}

package com.claymus.pagecontent.audit.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetAuditLogListRequest extends GenericRequest {

	private String cursor;
	
	private Integer resultCount;
	
	
	public String getCursor() {
		return cursor;
	}

	public void setCursor( String cursor ) {
		this.cursor = cursor;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount( Integer resultCount ) {
		this.resultCount = resultCount;
	}

}

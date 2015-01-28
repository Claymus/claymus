package com.claymus.pagecontent.audit.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetAuditLogContentRequest extends GenericRequest {

	private String cursor;
	
	private int resultCount;
	
	
	public GetAuditLogContentRequest() {
		super( null );
	}
	
	
	public String getCursor() {
		return cursor;
	}

	public void setCursor( String cursor ) {
		this.cursor = cursor;
	}


	public int getResultCount() {
		return resultCount;
	}


	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

}

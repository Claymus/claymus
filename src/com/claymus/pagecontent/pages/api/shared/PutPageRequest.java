package com.claymus.pagecontent.pages.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutPageRequest extends GenericRequest {

	private Long id;
	private boolean hasId;
	
	@Validate( required = true, regEx = REGEX_URI )
	private String uriAlias;
	private boolean hasUriAlias;

	@Validate( required = true )
	private String title;
	private boolean hasTitle;
	
	
	public PutPageRequest( String accessToken ) {
		super( null );
	}

	
	public Long getId() {
		return id;
	}
	
	public boolean hasId() {
		return hasId;
	}
	
	public String getUriAlias() {
		return uriAlias;
	}

	public boolean hasUriAlias() {
		return hasUriAlias;
	}
	
	public String getTitle() {
		return title;
	}

	public boolean hasTitle() {
		return hasTitle;
	}
	
}

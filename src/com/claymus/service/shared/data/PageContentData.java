package com.claymus.service.shared.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PageContentData implements IsSerializable {
	
	private Long id;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

}

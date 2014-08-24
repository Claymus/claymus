package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetUserBookListRequest implements IsSerializable {
	
	private Long bookId;
	private Long userId;
	
	
	@SuppressWarnings("unused")
	private GetUserBookListRequest() {}
	
	public GetUserBookListRequest( Long userId, Long bookId ) {
		this.bookId = bookId;
		this.userId = userId;
	}
	
	
	public Long getBookId() {
		return this.bookId;
	}
	
	public Long getUserId(){
		return this.userId;
	}

}
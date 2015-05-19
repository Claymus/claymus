package com.claymus.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CommentFilter implements IsSerializable {
	
	private String parentId;
	
	private CommentParentType parentType;
	
	private Long userId;
	
	public String getParentId(){
		return parentId;
	}
	
	public void setParentId( String parentId ){
		this.parentId = parentId;
	}
	
	public CommentParentType getParentType(){
		return parentType;
	}
	
	public void setParentType( CommentParentType parentType ){
		this.parentType = parentType;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId( Long userId ){
		this.userId = userId;
	}
	
}

package com.claymus.pagecontent.comments.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.claymus.commons.shared.CommentParentType;

@SuppressWarnings( "serial" )
public class GetCommentListRequest extends GenericRequest {

	@Validate( required = true )
	private String parentId;
	
	@Validate( required = true )
	private CommentParentType parentType;
	
	private String cursor;
	private Boolean hasCursor;
	
	private Integer resultCount;
	private Boolean hasResultCount;
	
	public String getParentId(){
		return parentId;
	}
	
	public CommentParentType getParentType(){
		return parentType;
	}
	
	public String getCursor(){
		return cursor;
	}
	
	public Boolean hasCursor(){
		return hasCursor;
	}
	
	public Integer getResultCount(){
		return resultCount;
	}
	
	public Boolean hasResultCount(){
		return hasResultCount;
	}
}

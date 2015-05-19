package com.claymus.pagecontent.comments.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.claymus.data.transfer.shared.CommentData;

@SuppressWarnings( "serial" )
public class GetCommentListResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private List<CommentData> commentList;
	
	@SuppressWarnings( "unused" )
	private String cursor;
	
	public GetCommentListResponse( List<CommentData> commentDataList, String cursor ){
		this.commentList = commentDataList;
		this.cursor = cursor;
	}
}

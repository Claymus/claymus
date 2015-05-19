package com.claymus.pagecontent.comments.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.claymus.data.transfer.shared.CommentData;

@SuppressWarnings( "serial" )
public class PutCommentResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private CommentData commentData;
	
	public PutCommentResponse( CommentData commentData ){
		this.commentData = commentData;
	}
}

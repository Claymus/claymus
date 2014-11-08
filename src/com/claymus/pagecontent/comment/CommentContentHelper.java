package com.claymus.pagecontent.comment;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.comment.gae.CommentContentEntity;
import com.claymus.pagecontent.comment.shared.CommentContentData;

public class CommentContentHelper extends PageContentHelper<
		CommentContent,
		CommentContentData,
		CommentContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Comment List";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static CommentContent newCommentContent() {
		return new CommentContentEntity();
	}

}

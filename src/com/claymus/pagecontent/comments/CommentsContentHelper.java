package com.claymus.pagecontent.comments;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.comments.gae.CommentsContentEntity;
import com.claymus.pagecontent.comments.shared.CommentContentData;

public class CommentsContentHelper extends PageContentHelper<
		CommentsContent,
		CommentContentData,
		CommentsContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Comment List";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static CommentsContent newCommentContent() {
		return new CommentsContentEntity();
	}

}

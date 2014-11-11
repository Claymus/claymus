package com.claymus.pagecontent.comments;

import com.claymus.data.transfer.PageContent;

public interface CommentsContent extends PageContent {
	
	String getRefId();
	
	void setRefId( String refId );
	
}

package com.claymus.pagecontent.comment.gae;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.data.transfer.PageContent;
import com.claymus.pagecontent.comment.CommentContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class CommentContentEntity extends PageContentEntity implements CommentContent {
	

	@Persistent( column = "REFERENCE_ID" )
	private String refId;
	
	@NotPersistent
	private String cursor;
	
	@NotPersistent
	private Integer postCount;
	
	
	@Override
	public String getRefId() {
		return refId;
	}

	@Override
	public void setRefId(String refId) {
		this.refId = refId;
	}

	

	@Override
	public String getCursor() {
		return cursor;
	}

	@Override
	public void setCursor( String cursor ) {
		this.cursor = cursor;
	}

	@Override
	public Integer getPostCount() {
		return postCount;
	}

	@Override
	public void setPostCount( Integer postCount ) {
		this.postCount = postCount;
	}


}

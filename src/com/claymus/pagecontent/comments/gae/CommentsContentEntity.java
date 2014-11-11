package com.claymus.pagecontent.comments.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.comments.CommentsContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class CommentsContentEntity extends PageContentEntity
		implements CommentsContent {
	
	@Persistent( column = "REFERENCE_ID" )
	private String refId;
	

	@Override
	public String getRefId() {
		return refId;
	}

	@Override
	public void setRefId( String refId ) {
		this.refId = refId;
	}

}

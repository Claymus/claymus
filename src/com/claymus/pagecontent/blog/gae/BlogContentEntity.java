package com.claymus.pagecontent.blog.gae;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.blog.BlogContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class BlogContentEntity extends PageContentEntity implements BlogContent {

	@Persistent( column = "X_COL_0" )
	private String title;
	
	@NotPersistent
	private String cursor;
	
	@NotPersistent
	private Integer postCount;
	
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
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

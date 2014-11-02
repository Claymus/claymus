package com.claymus.pagecontent.blogpost.gae;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable
public class BlogPostContentEntity extends PageContentEntity implements BlogPostContent {
	
	@Persistent( column = "X_COL_0" )
	private String title;
	
	@Persistent( column = "X_COL_1" )
	private Text content;

	@Persistent( column = "X_COL_2" )
	private Long blogId;
	
	@NotPersistent
	private Boolean preview;

	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
	}

	@Override
	public String getContent() {
		return content == null ? null : content.getValue();
	}

	@Override
	public void setContent( String html ) {
		this.content = new Text( html );
	}

	@Override
	public Long getBlogId() {
		return blogId;
	}

	@Override
	public void setBlogId( Long blogId ) {
		this.blogId = blogId;
	}

	@Override
	public Boolean preview() {
		return preview == null ? false : preview;
	}

	@Override
	public void setPreview( Boolean preview ) {
		this.preview = preview;
	}

}

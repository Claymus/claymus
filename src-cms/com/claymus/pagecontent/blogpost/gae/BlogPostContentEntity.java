package com.claymus.pagecontent.blogpost.gae;

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
	public void setContent( String content ) {
		this.content = new Text( content );
	}

}

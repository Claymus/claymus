package com.claymus.pagecontent.blogpost;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.blogpost.gae.BlogPostContentEntity;
import com.claymus.pagecontent.blogpost.shared.BlogPostContentData;

public class BlogPostContentHelper extends PageContentHelper<
		BlogPostContent,
		BlogPostContentData,
		BlogPostContentProcessor> {
	
	private static final Access ACCESS_TO_ADD =
			new Access( "blogpost_content_add", false, "Add Post" );
	private static final Access ACCESS_TO_UPDATE =
			new Access( "blogpost_content_update", false, "Update Post" );

	
	@Override
	public String getModuleName() {
		return "Blog Post";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_ADD,
				ACCESS_TO_UPDATE };
	}
	

	public static BlogPostContent newBlogPostContent() {
		return new BlogPostContentEntity();
	}

	@Override
	public BlogPostContentData createData( BlogPostContent blogPostContent ) {
		BlogPostContentData blogPostContentData = new BlogPostContentData();
		blogPostContentData.setId( blogPostContent.getId() );
		blogPostContentData.setTitle( blogPostContent.getTitle() );
		blogPostContentData.setContent( blogPostContent.getContent() );
		if( blogPostContent.getId() == null ) // TODO: Hack for "New Blog" Page
			blogPostContentData.setPageUrl( "/" );
		else
			blogPostContentData.setPageUrl( "/author-interview/" + blogPostContent.getId() );
		blogPostContentData.setPreview( blogPostContent.preview() );
		return blogPostContentData;
	}
	
	@Override
	public BlogPostContent createOrUpdateFromData(
			BlogPostContentData blogPostContentData,
			BlogPostContent blogPostContent ) {
		
		if( blogPostContent == null )
			blogPostContent = newBlogPostContent();
		blogPostContent.setTitle( blogPostContentData.getTitle() );
		blogPostContent.setContent( blogPostContentData.getContent() );
		return blogPostContent;
	}


	@Override
	public boolean hasRequestAccessToAddContent( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_ADD );
	}
	
	@Override
	public boolean hasRequestAccessToUpdateContent( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE );
	}

}

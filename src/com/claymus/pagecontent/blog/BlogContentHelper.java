package com.claymus.pagecontent.blog;

import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.blog.gae.BlogContentEntity;
import com.claymus.pagecontent.blog.shared.BlogContentData;

public class BlogContentHelper extends PageContentHelper<
		BlogContent,
		BlogContentData,
		BlogContentProcessor> {

	@Override
	public String getModuleName() {
		return "Blog";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}


	public static BlogContent newPostContent() {
		return new BlogContentEntity();
	}

}

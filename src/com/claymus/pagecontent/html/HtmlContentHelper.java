package com.claymus.pagecontent.html;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.html.gae.HtmlContentEntity;
import com.claymus.pagecontent.html.shared.HtmlContentData;

public class HtmlContentHelper extends PageContentHelper<
		HtmlContent,
		HtmlContentData,
		HtmlContentProcessor> {
	
	private static final Access ACCESS_TO_ADD =
			new Access( "html_content_add", false, "Add Content" );
	private static final Access ACCESS_TO_UPDATE =
			new Access( "html_content_update", false, "Update Content" );

	
	@Override
	public String getModuleName() {
		return "Html Content";
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
	

	public static HtmlContent newHtmlContent() {
		return new HtmlContentEntity();
	}

	@Override
	public HtmlContent createOrUpdateFromData(
			HtmlContentData htmlContentData,
			HtmlContent htmlContent ) {
		
		if( htmlContent == null )
			htmlContent = newHtmlContent();
		htmlContent.setTitle( htmlContentData.getTitle() );
		htmlContent.setContent( htmlContentData.getContent() );
		return htmlContent;
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

package com.claymus.pagecontent;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.data.transfer.PageContent;
import com.claymus.service.shared.data.PageContentData;

public abstract class PageContentHelper<
		P extends PageContent,
		Q extends PageContentData,
		R extends PageContentProcessor<P> > {

	public abstract String getModuleName();
	
	public abstract Double getModuleVersion();

	public Access[] getAccessList() {
		return new Access[]{};
	};
	
	public boolean hasRequestAccessToAddContent( HttpServletRequest request ) {
		return false;
	};
	
	public boolean hasRequestAccessToUpdateContent( HttpServletRequest request ) {
		return false;
	};
	
	public P createOrUpdateFromData( Q pageContentData, P pageContent ) {
		return pageContent;
	};

}

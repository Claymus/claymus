package com.claymus.pagecontent.audit;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.ClaymusResource;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class AuditContentProcessor extends PageContentProcessor<AuditContent> {

	@Override
	public Resource[] getDependencies( AuditContent auditLogContent, HttpServletRequest request ) {
		return new Resource[] {
				ClaymusResource.JQUERY_2,
				ClaymusResource.POLYMER,
				ClaymusResource.POLYMER_CORE_AJAX,
				ClaymusResource.POLYMER_CORE_COLLAPSE,
				ClaymusResource.POLYMER_PAPER_SPINNER,
				new Resource() {
					
					@Override
					public String getTag() {
						return "<link rel='import' href='/polymer/pagecontent-audit-logs.html'>";
					}
					
				},
		};
	}
	
	@Override
	public String generateTitle( AuditContent auditContent, HttpServletRequest request ) {
		return "Audit";
	}
	
	@Override
	public String generateHtml( AuditContent auditContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! AuditContentHelper.hasRequestAccessToListAuditData( request ) )
			throw new InsufficientAccessException();

		ClaymusHelper claymusHelper = ClaymusHelper.get( request );
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "timeZone", claymusHelper.getCurrentUserTimeZone() );

		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName( request ) );
	}
	
}

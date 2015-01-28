<!-- PageContent :: Pages :: Audit -->

<#setting time_zone="${ timeZone }">

<div id="PageContent-AuditLogs" class="container">
	<pagecontent-auditlog apiUrl="/api.pratilipi/auditlog" pageSize=20></pagecontent-auditlog>
</div>



<script language="javascript">

	var pageContentAuditLog = document.querySelector( 'pagecontent-auditlog' );
	var pageContentAuditLogDiv = jQuery( '#PageContent-AuditLogs' );
	
	function pageContentAuditLogLoad() {
		if( pageContentAuditLog.isFinished )
			return;
			
		var heightReq = jQuery( window ).scrollTop()
				- pageContentAuditLogDiv.position().top
				+ jQuery( window ).height()
				+ 3 * jQuery( window ).height();

		if( pageContentAuditLogDiv.outerHeight( true ) > heightReq )
			return;
			
		pageContentAuditLog.loadAuditLogList();
	}

	jQuery( '#Polymer' ).bind( 'template-bound', function( e ) {
			pageContentAuditLogLoad();
	});
		
	jQuery( '#Polymer-Window' ).bind( 'scroll', function( e ) {
			pageContentAuditLogLoad();
	});
		
	jQuery( pageContentAuditLog ).bind( 'load-error load-success', function( e ) {
			pageContentAuditLogLoad();
	});
	
</script>

<!-- PageContent :: Audit Log :: End -->

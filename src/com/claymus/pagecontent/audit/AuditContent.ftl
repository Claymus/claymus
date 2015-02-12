<!-- PageContent :: Audit :: Start -->

<#setting time_zone="${ timeZone }">

<div id="PageContent-Audit" class="container">
	<pagecontent-audit-logs apiUrl="/api.pratilipi/audit/log/list" pageSize=10></pagecontent-audit-logs>
</div>



<script language="javascript">

	var pageContentAuditLog = document.querySelector( 'pagecontent-audit-logs' );
	var pageContentAuditLogDiv = jQuery( '#PageContent-Audit' );
	
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

<!-- PageContent :: Audit :: End -->

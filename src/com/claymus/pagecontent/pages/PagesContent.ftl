<!-- PageContent :: Pages :: Start -->


<div id="PageContent-Pages">
	<pagecontent-pages apiUrl="/api.pratilipi/page/list" pageSize=20></pagecontent-pages>
</div>


<script>

	var pageContentPages = document.querySelector( 'pagecontent-pages' );
	var pageContentPagesDiv = jQuery( '#PageContent-Pages' );
	
	function pageContentPagesLoad() {
		if( pageContentPages.isFinished )
			return;
			
		var heightReq = jQuery( window ).scrollTop()
				- pageContentPagesDiv.position().top
				+ jQuery( window ).height()
				+ 3 * jQuery( window ).height();

		if( pageContentPagesDiv.outerHeight( true ) > heightReq )
			return;
			
		pageContentPages.loadPageList();
	}

	jQuery( '#Polymer' ).bind( 'template-bound', function( e ) {
			pageContentPagesLoad();
	});
		
	jQuery( '#Polymer-Window' ).bind( 'scroll', function( e ) {
			pageContentPagesLoad();
	});
		
	jQuery( pageContentPages ).bind( 'load-error load-success', function( e ) {
			pageContentPagesLoad();
	});
	
</script>


<!-- PageContent :: Pages :: End -->
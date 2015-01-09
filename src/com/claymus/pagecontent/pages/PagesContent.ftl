<!-- PageContent :: Pages :: Start -->

<template is="auto-binding" id="PageContent-Pages">

	<div id="PageContent-Pages-List">
		<template repeat="{{ page in pageList }}">
			<div>
				<a href="{{ page['uriAlias'] }}">{{ page['title'] == null ? '(No Title)' : page['title'] }}</a>
				<br/>
				<span class="gray">{{ page['uriAlias'] }}</span>
			</div>
		</template>
		<div hidden?="{{ !isLoading }}">
			<paper-spinner active class="red"></paper-spinner>
			<span style="margin-left:10px;">Please wait...</span>
		</div>
		<div hidden?="{{ !isFinished }}">No more items !</div>
	</div>

	<core-ajax
			id="PageContent-Pages-AjaxGet"
			url="/api.pratilipi/page/list"
			contentType="application/json"
			method="GET"
			handleAs="json"
			on-core-response="{{ handleAjaxGetResponse }}"
			on-core-error="{{ handleAjaxGetError }}" ></core-ajax>

</template>


<script>

	var scope = document.querySelector( '#PageContent-Pages' );
	var pageListDiv;
	var ajaxGet;

	var cursor;
	var resultCount = 20;


	function loadPageList() {
		if( scope.isLoading || scope.isFinished )
			return;

		var heightReq = jQuery( window ).scrollTop()
				- pageListDiv.position().top
				+ jQuery( window ).height()
				+ 5 * jQuery( window ).height();

		if( pageListDiv.outerHeight( true ) > heightReq )
			return;
	
		scope.isLoading = true;
		ajaxGet.params = JSON.stringify( { cursor:cursor, resultCount:resultCount } );
		ajaxGet.go();
	};

	scope.handleAjaxGetResponse = function( event, response ) {
		cursor = response.response['cursor'];
		var pageList = response.response['pageList'];
		for( var i = 0; i < pageList.length; i++ )
			scope.pageList.push( pageList[i] );

		scope.isFinished = pageList.length < resultCount;
		scope.isLoading = false;
		loadPageList();
	};
	
	scope.handleAjaxGetError = function( event, response ) {
		scope.isLoading = false;
		loadPageList();
	};
	
	addEventListener( 'template-bound', function( e ) {

		if( e.target != scope )
			return;
		
		pageListDiv = jQuery( '#PageContent-Pages-List' );
		ajaxGet = document.querySelector( '#PageContent-Pages-AjaxGet' );
		
		scope.pageList = [];
		loadPageList();

		jQuery( window ).scroll( function() {
			loadPageList();
		});
	
	});

</script>


<style>

	#PageContent-Pages-List div {
		padding: 10px;
		margin: 10px;
		border-radius: 10px;
	}

</style>

<!-- PageContent :: Pages :: End -->
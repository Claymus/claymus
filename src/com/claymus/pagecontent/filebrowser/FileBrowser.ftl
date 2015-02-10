<!-- PageContent :: File Browser :: Start -->

<div class="container well" style="margin-top:20px;">
	<div id="Pratilipi-FileBrowser-ImageLibrary" style="width:100px;">
		<#list imageUrlList as imageUrl>
			<img src="${ imageUrl }" onClick = "imageClicked(this)" style="cursor: pointer;width: 100%;"/>
		</#list>
		<script type="text/javascript">
			
			function getUrlParam( paramName ) {
			    var reParam = new RegExp( '(?:[\?&]|&)' + paramName + '=([^&]+)', 'i' ) ;
			    var match = window.location.search.match(reParam) ;
				console.log( reParam );
				console.log( ( match && match.length > 1 ) ? match[ 1 ] : null );
			    return ( match && match.length > 1 ) ? match[ 1 ] : null ;
			}
			
		    function imageClicked(element){
				var imageUrl = $( element ).attr( "src" );
				var funcNum = getUrlParam( 'CKEditorFuncNum' );
				window.opener.CKEDITOR.tools.callFunction( funcNum, imageUrl);  
				window.close();
		    };
		</script>
	</div>
	<div id="Pratilipi-FileBrowser-Cancel" class="btn btn-info" style="margin-top: 20px;" onclick="window.close();">
		Cancel
	</div>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.filebrowser/pagecontent.filebrowser.nocache.js" defer></script>

<!-- PageContent :: File Browser :: End -->
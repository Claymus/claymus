<!-- PageContent :: File Browser :: Start -->

<div  style="margin : 10px;">
	<div id="Pratilipi-FileBrowser-ImageLibrary">
		<#list nameList as name>
			<img src="${ urlPrefix }${ name }" onClick = "imageClicked(this)" style="cursor: pointer; margin: 5px; display: inline; width: 150px;"/>
		</#list>
		<script type="text/javascript">
			
			function getUrlParam( paramName ) {
			    var reParam = new RegExp( '(?:[\?&]|&)' + paramName + '=([^&]+)', 'i' ) ;
			    var match = window.location.search.match(reParam) ;
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


<!-- PageContent :: File Browser :: End -->
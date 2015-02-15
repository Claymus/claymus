<!-- PageContent :: File Browser :: Start -->


<div style="margin:10px;">
	<#list nameList as name>
		<img src="${ urlPrefix }${ name }" onClick="fileBrowserImageClicked(this)" style="cursor:pointer; margin:5px; display:inline; width:150px;"/>
	</#list>
</div>


<script>
	
    function fileBrowserImageClicked( element ) {
		var imageUrl = $( element ).attr( "src" );
		var funcNum = getUrlParam( 'CKEditorFuncNum' );
		window.opener.CKEDITOR.tools.callFunction( funcNum, imageUrl );  
		window.close();
    };

	
</script>


<!-- PageContent :: File Browser :: End -->
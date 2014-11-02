<!-- PageContent :: File Browser :: Start -->

<div class="container well" style="margin-top:20px;">
	<div id="Pratilipi-FileBrowser-ImageLibrary">
		<#list imageUrlList as imageUrl>
			<img src="${ imageUrl }" onClick = "imageClicked(this)" style="cursor: pointer;"/>
		</#list>
		<script type="text/javascript">
			
		    function imageClicked(element){
				var imageUrl = $( element ).attr( "src" );
				//'CKEditorFuncNum=1' is URL parameter. Couldn't find interesting way to get url parameter value, hence hardcoded it.
				window.opener.CKEDITOR.tools.callFunction( 1, imageUrl);  
				window.close();
		    };
		</script>
	</div>
	<div id="Pratilipi-FileBrowser-ImageUpload" style="margin-top: 20px;"></div>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.filebrowser/pagecontent.filebrowser.nocache.js" defer></script>

<!-- PageContent :: File Browser :: End -->
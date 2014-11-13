<!DOCTYPE html>
<html lang="en">
	<head>
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

		<link rel="stylesheet" href="//static.pratilipi.com/third-party/bootstrap-3.2.0/css/bootstrap.min.css">
	
		<script src="//ajax.googleapis.com/ajax/libs/webfont/1.5.3/webfont.js"></script>
		<script>
			WebFont.load({
				google: {
					families: [ 'Noto+Sans:400,700,400italic,700italic:latin' ]
				}
			});
		</script>

		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" defer></script>
		<script src="//static.pratilipi.com/third-party/bootstrap-3.2.0/js/bootstrap.min.js" defer></script>
	
		<script src="//cdn.ckeditor.com/4.4.3/full/ckeditor.js" charset="utf-8" defer></script>
		<script language="javascript" defer>
			window.onload = function() {
				CKEDITOR.config.toolbar = [
						['Source','Format','Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
						['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','Outdent','Indent'],
						['NumberedList','BulletedList'],
						['Blockquote','Smiley','HorizontalRule','PageBreak'],
						['Link','Unlink'],
						['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],
						['ShowBlocks','Maximize']];
				CKEDITOR.config.toolbar_BASIC = [
						['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
						['NumberedList','BulletedList'],
						['Blockquote','Smiley','HorizontalRule'],
						['Link','Unlink']];
			}
		</script>
		
		<script src="//static.pratilipi.com/third-party/jquery-file-upload-9.7.0/js/vendor/jquery.ui.widget.js" defer></script>
		<script src="//static.pratilipi.com/third-party/jquery-file-upload-9.7.0/js/jquery.iframe-transport.js" defer></script>
		<script src="//static.pratilipi.com/third-party/jquery-file-upload-9.7.0/js/jquery.fileupload.js" defer></script>
		
	
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="//oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="//oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
	
	
		<link type="text/css" rel="stylesheet" href="/theme.default/style.css">

		<script type="text/javascript" language="javascript" src="/theme.default/script.js" defer></script>
		<script type="text/javascript" language="javascript" src="/pagecontent.userforms/pagecontent.userforms.nocache.js" async></script>
	
		<title>${ (page.getTitle() + " | Claymus") ! "Claymus" }</title>		
		
	</head>
	<body>
		
		<#if websiteWidgetHtmlListMap["HEADER"] ??>
			<#list websiteWidgetHtmlListMap["HEADER"] as websiteWidgetHtml>
				${ websiteWidgetHtml }
			</#list>
		</#if>
		
		<#list pageContentHtmlList as pageContentHtml>
			${ pageContentHtml }
		</#list>
		
		<#if websiteWidgetHtmlListMap["FOOTER"] ??>
			<#list websiteWidgetHtmlListMap["FOOTER"] as websiteWidgetHtml>
				${ websiteWidgetHtml }
			</#list>
		</#if>

	</body>
</html>

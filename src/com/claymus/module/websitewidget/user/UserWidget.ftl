<div class="websitewidget-user">

	<div>
		<#if getCurrentUser() ??>
			Hello, ${ getCurrentUser().getNickname() } ! (<a href="${ createLogoutURL() }">logout</a>)
		<#else>
			Hello, Guest ! (<a href="${ createLoginURL() }">login</a>)
		</#if>
	</div>

</div>
<#macro facebook shareUrl>
	<!-- Facebook like and share buttons -->
	<div class="fb-like" data-href="${ shareUrl }" data-layout="button_count" data-action="like" data-show-faces="true" data-share="true"></div>
</#macro>

<#macro google shareUrl>
	<!-- Google+ follow and share button -->
	<div class="g-plus" data-action="share" data-annotation="bubble" data-href="${ shareUrl }" style="line-height: 25px;"></div>
</#macro>

<#macro twitter shareUrl>
	<!-- Twitter tweet button -->
	<a href="https://twitter.com/share" class="twitter-share-button" data-url="${ shareUrl }"></a>
</#macro>


<#macro toolbar shareUrl>
	<div style="line-height:10px;">
		<@facebook shareUrl=shareUrl />
		<@google shareUrl=shareUrl />
		<@twitter shareUrl=shareUrl />
	</div>
</#macro>

<#macro vToolbar shareUrl>
	<div style="text-align: left;">
		<div style="margin-bottom:5px;">
			<@facebook shareUrl=shareUrl />
		</div>
		<div>
			<@google shareUrl=shareUrl />
		</div>
		<div>
			<@twitter shareUrl=shareUrl />
		</div>
	</div>
</#macro>
<#macro toolbar shareUrl>
	<div style="line-height:10px;">
		<!-- Facebook like and share button -->
		<div class="fb-like" data-href="${ shareUrl }" data-layout="button_count" data-action="like" data-show-faces="true" data-share="true"></div>
		<!-- Google+ follow and share button -->
		<div class="g-plus" data-action="share" data-annotation="bubble" data-href="${ shareUrl }" style="line-height: 25px;"></div>
		<!-- Twitter tweet button -->
		<a href="https://twitter.com/share" class="twitter-share-button" data-url="${ shareUrl }"></a>
	</div>
</#macro>

<#macro vToolbar shareUrl>
	<div style="text-align: left;">
		<div style="margin-bottom:5px;">
			<!-- Facebook like and share buttons -->
			<div class="fb-like" data-href="${ shareUrl }" data-layout="button_count" data-action="like" data-show-faces="true" data-share="true"></div>
		</div>
		<div>
			<!-- Google+ follow and share button -->
			<div class="g-plus" data-action="share" data-annotation="bubble" data-href="${ shareUrl }" style="line-height: 25px;"></div>
		</div>
		<div>
			<!-- Twitter tweet button -->
			<a href="https://twitter.com/share" class="twitter-share-button" data-url="${ shareUrl }"></a>
		</div>
	</div>
</#macro>
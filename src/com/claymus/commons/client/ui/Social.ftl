<#macro facebook_pratilipi shareUrl likes>
	<!-- Facebook like and share buttons -->
	<div class="fb-like" data-href="${ shareUrl }" data-layout="button" data-action="like" data-show-faces="true" data-share="true"></div>
	<div style="display: table-cell;padding-left: 3px;vertical-align: bottom;padding-bottom: 0px;">
		<div style="display: table-cell;
				    height: 20px;
				    padding-left: 5px;
				    padding-right: 5px;
				    padding-bottom: 1px;
				    font-size: 11px;
				    margin-left: 5px;
				    font-family: helvetica, arial, sans-serif;
				    color: #141823;
				    border: 1px solid grey;
				    border-radius: 2px;
				    text-align: center;
				    vertical-align: bottom;
				    opacity: 0.6;">${ likes }</div>
	</div>
</#macro>

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
		<div style="margin-bottom:5px; display=table;">
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

<#macro pratilipi shareUrl likes>
	<div style="text-align: left;">
		<div style="margin-bottom:5px;display:table;">
			<@facebook_pratilipi shareUrl=shareUrl likes=likes />
		</div>
		<div>
			<@google shareUrl=shareUrl />
		</div>
		<div>
			<@twitter shareUrl=shareUrl />
		</div>
	</div>
</#macro>
<#macro comments review commentDataList userData reviewLikesList reviewDislikesList commentLikesMap commentDislikesMap>

<#assign commentDataSeq=commentDataList?split( "~" ) reviewLikesSeq=reviewLikesList?split( "~" ) reviewDislikesSeq=reviewDislikesList?split( "~" )>

	<div id="PageContent-Pratilipi-Comment">
		<div id="PageContent-Pratilipi-Comment-FormDiv"  data-id="${ review.getId() }" data-type="REVIEW">
			<#if userData.getEmail()?? && review.getUserId() != userData.getId()>
				<#if reviewLikesSeq?seq_contains( userData.getId()?c )>
					<img onclick="vote( this )" src="/theme.pratilipi/images/upvoted.png" title="Upvote" alt="Upvote" data-value="0" />
				<#else>
					<img onclick="vote( this )" src="/theme.pratilipi/images/upvote.png" title="Upvote" alt="Upvote" data-value="1" />
				</#if>
			<#else>
				<img class="disabled" src="/theme.pratilipi/images/upvote_disabled.png" title="Upvote" alt="Upvote" data-value="0" />
			</#if>
			<#if reviewLikesList?? && reviewLikesList?length gt 0>
				<span id="PageContent-Pratilipi-Review-Likes">${ reviewLikesSeq?size }</span>
			<#else>
				<span id="PageContent-Pratilipi-Review-Likes"></span>
			</#if>
		
			<#if userData.getEmail()?? && review.getUserId() != userData.getId()>
				<#if reviewDislikesSeq?seq_contains( userData.getId()?c )>
					<img onclick="vote( this )" src="/theme.pratilipi/images/downvoted.png" title="Downvote" alt="Downvote" data-value="0"/>
				<#else>
					<img onclick="vote( this )" src="/theme.pratilipi/images/downvote.png" title="Downvote" alt="Downvote" data-value="1"/>
				</#if>
			<#else>
				<img class="disabled" src="/theme.pratilipi/images/downvote_disabled.png" title="Downvote" alt="Downvote" data-value="0" />
			</#if>			
			<#if reviewDislikesList?? && reviewDislikesList?length gt 0>
				<span id="PageContent-Pratilipi-Review-Dislikes">${ reviewDislikesSeq?size }</span>
			<#else>
				<span id="PageContent-Pratilipi-Review-Dislikes"></span>
			</#if>
			
			<br/>
			<textarea id="PageContent-Pratilipi-Comment-Form-Textarea" placeholder="Reply to above review" rows=2 cols=80 onkeydown="onEnter(this)" style="margin-top: 10px"></textarea>
		</div>
		
		
		<div id="PageContent-Pratilipi-Comment-List">
			<#list commentDataSeq as commentData>
				<#assign commentSeq=commentData?split( "_" )>
				<#if commentSeq?size == 5>
					<div class="comment" data-id="${ commentSeq[0] }" data-type="COMMENT">
						<p>
							<b>${ commentSeq[2] } </b>
							${ commentSeq[3] }
							</br>
							<small>${ commentSeq[4]?date }</small>
						</p>
						
						<#assign commentLikesSeq=commentLikesMap[ commentSeq[0] ]?split( "~" )>
						<#if userData.getEmail()?? && userData.getId()?c != commentSeq[1]>
							<#if commentLikesSeq?seq_contains( userData.getId()?c )>
								<img onclick="vote( this )" src="/theme.pratilipi/images/upvoted.png" title="Upvote" alt="Upvote" src="/war/image/like.jpg" data-value="0">
							<#else>
								<img onclick="vote( this )" src="/theme.pratilipi/images/upvote.png" title="Upvote" alt="Upvote" src="/war/image/like.jpg" data-value="1">
							</#if>
						<#else>
							<img class="disabled" src="/theme.pratilipi/images/upvote_disabled.png" title="Upvote" alt="Upvote" src="/war/image/like.jpg" data-value="0">
						</#if>
						<#if commentLikesMap[ commentSeq[0] ]?? && commentLikesMap[ commentSeq[0] ]?length gt 0>
							<span id="PageContent-Pratilipi-Comment-Likes">${ commentLikesSeq?size }</span>
						<#else>
							<span id="PageContent-Pratilipi-Comment-Likes"></span>
						</#if>
						
						<#assign commentDislikesSeq=commentDislikesMap[ commentSeq[0] ]?split( "~" )>
						<#if userData.getEmail()?? && userData.getId()?c != commentSeq[1]>
							<#if commentDislikesSeq?seq_contains( userData.getId()?c )>
								<img onclick="vote( this )" src="/theme.pratilipi/images/downvote.png" title="Downvote" alt="Downvote" src="/war/image/like.jpg" data-value="0">
							<#else>
								<img onclick="vote( this )" src="/theme.pratilipi/images/downvoted.png" title="Downvote" alt="Downvote" src="/war/image/like.jpg" data-value="1">
							</#if>
						<#else>
							<img class="disabled" src="/theme.pratilipi/images/downvote_disabled.png" title="Downvote" alt="Downvote" src="/war/image/like.jpg" data-value="0">
						</#if>
						<#if commentDislikesMap[ commentSeq[0] ]?? && commentDislikesMap[ commentSeq[0] ]?length gt 0>
							<span id="PageContent-Pratilipi-Comment-Dislikes">${ commentDislikesSeq?size }</span>
						<#else>
							<span id="PageContent-Pratilipi-Comment-Dislikes"></span>
						</#if>
					</div>
				</#if>
			</#list>
		</div>
	</div>
	
	<style>
		#PageContent-Pratilipi-Comment img{
			cursor : pointer;
			width:  24px;
		}
		
		#PageContent-Pratilipi-Review-Likes, #PageContent-Pratilipi-Comment-Likes {
		    font-size: 14px;
		    color: rgb(57, 181, 74);
		    padding : 3px 14px 3px 0px;
		}
		
		#PageContent-Pratilipi-Review-Dislikes, #PageContent-Pratilipi-Comment-Dislikes {
		    font-size: 14px;
		    color: rgb(193, 39, 45);
		    padding : 3px;
		}
		
		#PageContent-Pratilipi-Comment-List {
			margin-top : 10px;
		}
		
		.comment {
			padding-left : 10px;
			font-size : 15px;
		}
		
		small {
			font-size : 80%;
		}
		
		.disabled {
			cursor : default !important;
		}
	</style>
	
	
	<script>
	
		var requestType;
		
		function ajaxPost( object ){
			var tagName = jQuery( object ).prop( "tagName" );
			var parent = jQuery( object ).parent();
			var requestData;
			var parentId = parent.attr( "data-id" );
			var parentType = parent.attr( "data-type" );
			
			if( tagName == 'IMG' ){
				requestType = "vote";
				var value = parseInt( jQuery( object ).attr( "data-value" ) );
				var voteType = jQuery( object ).attr( "title" );
				if( voteType == 'Upvote' )
					requestData = JSON.stringify({
										parentId: parentId, 
										parentType: parentType, 
										upvote:value
									});
				else
					requestData = JSON.stringify({
										parentId: parentId, 
										parentType: parentType, 
										downvote:value
									});
			}
			else {
				requestType = "comment";
				if( parentType == 'REVIEW' ) {
					requestData = JSON.stringify({
										parentId: parentId, 
										parentType: parentType,
										comment:jQuery( object ).val()
									});
				} else {
					requestData = JSON.stringify({
										id: parentId, 
										parentType: parentType,
										comment:jQuery( object ).val()
									});
				}
			}
			
			jQuery.ajax({
				url: "/api.pratilipi/comment",
				type: "PUT",
				contentType: "application/json",
				dataType: "json",
				handleAs: "json",
				data: requestData,
				beforeSend: function( data, object ){
				},
				success: function( response, status, xhr ) {
					handleAjaxPostResponse(object, response );
				}, 
				error: function( xhr, status, error) {
					alert( status + " : " + error );
				},
				complete: function( event, response ){
					console.log( response );
				}
			});
		}
		
		function handleAjaxPostResponse( object, response ){
			var newComment = response[ 'commentData' ];
			var title = jQuery( object ).attr( 'title' ).toLowerCase();
			var latestComment = jQuery( '#PageContent-Pratilipi-Comment-List' ).first();
			if( requestType == "vote" ){
				var span = jQuery( object ).next();
				var currentVotes = span.text();
				if( span.is(':empty') )
					span.text( parseInt( 1 ));
				else {
					if( parseInt( newComment[ title ] ) > 0 )
						span.text( parseInt( currentVotes ) + 1 );
					else 
						span.text( parseInt( currentVotes ) - 1 );
				}
					
				var src = "/theme.pratilipi/images/" + title;
				
				console.log( "Object clicked : " + jQuery( object ).prop( "tagName" ) );
				
				if( parseInt( newComment[ title ] ) == 1 ){
					jQuery( object ).attr( 'src', src + 'd.png' );
					jQuery( object ).attr( 'data-value', '0' );
				} else {
					jQuery( object ).attr( 'src', src + '.png' );
					jQuery( object ).attr( 'data-value', '1' );
				}
			} else if( requestType == "comment" ){
				latestComment.attr( "data-id", newComment[ 'id' ] );
				latestComment.attr( "data-type", newComment[ 'parentType' ] );
			}
		}
		
		function postComment(object){
			addComment( object.value );
			ajaxPost( object )
		}
		
		function onEnter(object){
			if (event.which == 13) {
				event.preventDefault();
				postComment(object);
				object.value = "";
				object.blur();
				return false;
			 }
		}
		
		function addComment( comment){
			var userName = '<b>${ userData.getName() }</b> ';
			var date = '<br/><small>Now</small>';
			var commentList = jQuery( "#PageContent-Pratilipi-Comment-List" );
			var newCommentDiv =jQuery( "<div></div>" );
			newCommentDiv.addClass( "comment" );
			commentList.prepend( newCommentDiv );
			var commentString =jQuery( "<p></p>" );
			commentString.html( userName + comment + date );
			newCommentDiv.append( commentString );
		}
		
		function vote( object ){
			ajaxPost( object );
		}
	</script>
</#macro>
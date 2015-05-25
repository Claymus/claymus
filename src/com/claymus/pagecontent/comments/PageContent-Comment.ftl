<#macro comments review commentDataList userData reviewLikesList reviewDislikesList commentLikesMap commentDislikesMap>

<#assign commentDataSeq=commentDataList?split( "~" ) reviewLikesSeq=reviewLikesList?split( "~" ) reviewDislikesSeq=reviewDislikesList?split( "~" )>

	<div class="Comment-Div">
		<div  data-id="${ review.getId() }" data-type="REVIEW">
			<#if userData.getEmail()?? && review.getUserId() != userData.getId()>
				<#if reviewLikesSeq?seq_contains( userData.getId()?c )>
					<img class="Like-Img" onclick="vote( this )" src="/theme.pratilipi/images/upvoted.png" title="Upvote" alt="Upvote" data-value="0" />
				<#else>
					<img class="Like-Img" onclick="vote( this )" src="/theme.pratilipi/images/upvote.png" title="Upvote" alt="Upvote" data-value="1" />
				</#if>
			<#else>
				<img class="Disabled" src="/theme.pratilipi/images/upvote_disabled.png" title="Upvote" alt="Upvote" data-value="0" />
			</#if>
			<#if reviewLikesList?? && reviewLikesList?length gt 0>
				<span class="Likes">${ reviewLikesSeq?size }</span>
			<#else>
				<span class="Likes"></span>
			</#if>
		
			<#if userData.getEmail()?? && review.getUserId() != userData.getId()>
				<#if reviewDislikesSeq?seq_contains( userData.getId()?c )>
					<img class="Dislike-Img" onclick="vote( this )" src="/theme.pratilipi/images/downvoted.png" title="Downvote" alt="Downvote" data-value="0"/>
				<#else>
					<img class="Dislike-Img" onclick="vote( this )" src="/theme.pratilipi/images/downvote.png" title="Downvote" alt="Downvote" data-value="1"/>
				</#if>
			<#else>
				<img class="Disabled" src="/theme.pratilipi/images/downvote_disabled.png" title="Downvote" alt="Downvote" data-value="0" />
			</#if>			
			<#if reviewDislikesList?? && reviewDislikesList?length gt 0>
				<span class="Dislikes">${ reviewDislikesSeq?size }</span>
			<#else>
				<span class="Dislikes"></span>
			</#if>
			
			<br/>
			<#if userData.getEmail()??>
				<textarea placeholder="Reply to above review" rows=2 onkeydown="onEnter(this)"></textarea>
			<#else>
				<textarea placeholder="Reply to above review" rows=2 data-toggle='modal' data-target="#loginModal" onclick="window.location.href='#Comment'"></textarea>
			</#if>
		</div>
		
		
		<div id="PageContent-Pratilipi-Comment-List-${ review.getId() }" class="Comment-List">
			<#list commentDataSeq as commentData>
				<#assign commentSeq=commentData?split( "_" )>
				<#if commentSeq?size == 5>
					<div class="Comment" data-id="${ commentSeq[0] }" data-type="COMMENT">
						<p>
							<b>${ commentSeq[2] } </b>
							${ commentSeq[3] }
							</br>
							<small>${ commentSeq[4]?date }</small>
						</p>
						
						<#assign commentLikesSeq=commentLikesMap[ commentSeq[0] ]?split( "~" )>
						<#if userData.getEmail()?? && userData.getId()?c != commentSeq[1]>
							<#if commentLikesSeq?seq_contains( userData.getId()?c )>
								<img class="Like-Img" onclick="vote( this )" src="/theme.pratilipi/images/upvoted.png" title="Upvote" alt="Upvote" src="/war/image/like.jpg" data-value="0">
							<#else>
								<img class="Like-Img" onclick="vote( this )" src="/theme.pratilipi/images/upvote.png" title="Upvote" alt="Upvote" src="/war/image/like.jpg" data-value="1">
							</#if>
						<#else>
							<img class="Disabled" src="/theme.pratilipi/images/upvote_disabled.png" title="Upvote" alt="Upvote" src="/war/image/like.jpg" data-value="0">
						</#if>
						<#if commentLikesMap[ commentSeq[0] ]?? && commentLikesMap[ commentSeq[0] ]?length gt 0>
							<span class="Likes">${ commentLikesSeq?size }</span>
						<#else>
							<span class="Likes"></span>
						</#if>
						
						<#assign commentDislikesSeq=commentDislikesMap[ commentSeq[0] ]?split( "~" )>
						<#if userData.getEmail()?? && userData.getId()?c != commentSeq[1]>
							<#if commentDislikesSeq?seq_contains( userData.getId()?c )>
								<img class="Dislike-Img" onclick="vote( this )" src="/theme.pratilipi/images/downvote.png" title="Downvote" alt="Downvote" src="/war/image/like.jpg" data-value="0">
							<#else>
								<img class="Dislike-Img" onclick="vote( this )" src="/theme.pratilipi/images/downvoted.png" title="Downvote" alt="Downvote" src="/war/image/like.jpg" data-value="1">
							</#if>
						<#else>
							<img class="Disabled" src="/theme.pratilipi/images/downvote_disabled.png" title="Downvote" alt="Downvote" src="/war/image/like.jpg" data-value="0">
						</#if>
						<#if commentDislikesMap[ commentSeq[0] ]?? && commentDislikesMap[ commentSeq[0] ]?length gt 0>
							<span class="Dislikes">${ commentDislikesSeq?size }</span>
						<#else>
							<span class="Dislikes"></span>
						</#if>
					</div>
				</#if>
			</#list>
		</div>
	</div>
	
	<style>
		.Comment-Div img{
			cursor : pointer;
			width:  24px;
		}
		
		.Comment-Div textarea {
			margin-top: 10px;
			width: 50%;
		}
		
		.Likes {
		    font-size: 14px;
		    color: rgb(57, 181, 74);
		    padding : 3px 14px 3px 0px;
		}
		
		.Dislikes {
		    font-size: 14px;
		    color: rgb(193, 39, 45);
		    padding : 3px;
		}
		
		.Comment-List {
			margin-top : 10px;
		}
		
		.Comment {
			padding-left : 10px;
			font-size : 15px;
		}
		
		small {
			font-size : 80%;
		}
		
		.Disabled {
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
				beforeSend: function(){
					if( tagName == 'IMG' )
						setVote( object, tagName );
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
			var latestComment = jQuery( '#PageContent-Pratilipi-Comment-List-${ review.getId() }' ).first();
			if( requestType == "vote" ){
				var src = "/theme.pratilipi/images/" + title;
				
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
			addComment( object );
			ajaxPost( object )
		}
		
		function vote( object ){
			ajaxPost( object );
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
		
		function  setVote( object ){
			var title;
			if( jQuery( object ).attr( 'title' ) == null || jQuery( object ).attr( 'title' ) == '' )
				return;
			else
				title = jQuery( object ).attr( 'title' ).toLowerCase();
				
			var span = jQuery( object ).next();
			var parent = jQuery( object ).parent();
			var value = jQuery( object ).attr( 'data-value' );
			var currentVotes = span.text();
			var sibling;
			var siblineImg;
			var src = "/theme.pratilipi/images/" + title;
			
			if( title == 'upvote' ){
				sibling = parent.find( '.Dislikes' );
				siblingImg = parent.find( '.Dislike-Img' );
				if( siblingImg.attr( 'data-value' ) < 1 ){
					siblingImg.attr( 'src', '/theme.pratilipi/images/downvote.png' );
					siblingImg.attr( 'data-value', '1' );
					var dislikes = sibling.text();
					sibling.text( parseInt( dislikes ) - 1 );
				}
			}
			else if( title == 'downvote' ){
				sibling = parent.find( '.Likes' );
				siblingImg = parent.find( '.Like-Img' );
				if( siblingImg.attr( 'data-value' ) < 1 ) {
					siblingImg.attr( 'src', '/theme.pratilipi/images/upvote.png' );
					siblingImg.attr( 'data-value', '1' );
					var likes = sibling.text();
					sibling.text( parseInt( likes ) - 1 );
				}
			}

			if( span.is(':empty') )
				span.text( parseInt( 1 ));
			else {
				if( parseInt( value ) > 0 )
					span.text( parseInt( currentVotes ) + 1 );
				else 
					span.text( parseInt( currentVotes ) - 1 );
			}
			
			if( parseInt( value ) == 1 ){
				jQuery( object ).attr( 'src', src + '.png' );
				jQuery( object ).attr( 'data-value', '0' );
			} else {
				jQuery( object ).attr( 'src', src + 'd.png' );
				jQuery( object ).attr( 'data-value', '1' );
			}
		}
		
		function addComment( object ){
			var userName = '<b>${ userData.getName() }</b> ';
			var date = '<br/><small>Now</small>';
			var commentList = jQuery( object ).parent().next();
			var newCommentDiv =jQuery( "<div></div>" );
			newCommentDiv.addClass( "Comment" );
			commentList.prepend( newCommentDiv );
			var commentString =jQuery( "<p></p>" );
			commentString.html( userName + object.value + date );
			newCommentDiv.append( commentString );
		}
		
	</script>
</#macro>
/* For Facebook like/share button */
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

/* For Twitter tweet button */
!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');


/* Bootstrap components */
jQuery( ".dropdown-menu li a" ).click(function() {
	jQuery(this).parents( ".input-group-btn" ).find( '.btn' ).html( $(this).text() + " <span class='caret'></span>" );
	jQuery(this).parents( ".input-group-btn" ).find( 'input' ).val( $(this).data( 'value' ) );
});

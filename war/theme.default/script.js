/* Loading ... */
var isLoading = true;

function loading( bool ) {
	isLoading = bool;
	if( isLoading )
		jQuery( "#Claymus-Loading" ).fadeIn( "slow", function() { animateLoading(); } );
	else
		jQuery( "#Claymus-Loading" ).fadeOut( "slow" );
}

function animateLoading() {
	var str = 'Loading';
	var n = new Date().getSeconds() % 4;
	switch( n ) {
		case 0: str = str + "&nbsp;&nbsp;&nbsp;"; break;
		case 1: str = str + ".&nbsp;&nbsp;"; break;
		case 2: str = str + "..&nbsp;"; break;
		case 3: str = str + "..."; break;
	}
	jQuery( "#Claymus-Loading" ).html( str );
	if( isLoading )
		window.setTimeout( animateLoading, 1000 );
}

animateLoading( true );
jQuery( window ).load( function() {
	loading( false );
});


/* For Facebook like/share button */
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=293990794105516&version=v2.2";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


/* For Twitter tweet button */
!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');

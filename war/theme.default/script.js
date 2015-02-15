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


/* Cookie Manipulation */

function getCookie( cname ) {
	var name = cname + "=";
	var ca = document.cookie.split( ';' );
	for( var i = 0; i < ca.length; i++ ) {
		var c = ca[i];
		while( c.charAt(0)==' ' ) c = c.substring(1);
		if( c.indexOf(name) != -1 ) {
			return c.substring( name.length );
		}
	}
	return "";
}

function setCookie( cname, cvalue, exdays, path ) {
	if( exdays ) {
		var d = new Date();
		d.setTime( d.getTime() + (exdays*24*60*60*1000) );
		var expires = "; expires=" + d.toUTCString();
	} else {
		var expires = "";
	}
	
	if( path ) {
		if( path.indexOf( "?" ) != -1 )
			path = path.substring( 0, path.indexOf( "?" ) );
		path = "; path=" + path;
	} else {
		path = "; path=" + window.location.pathname;
	}
	document.cookie = cname + "=" + cvalue + expires + path;
}


/* Url Parameters */

function getUrlParam( paramName ) {
    var reParam = new RegExp( '(?:[\?&]|&)' + paramName + '=([^&]+)', 'i' ) ;
    var match = window.location.search.match( reParam ) ;
    return ( match && match.length > 1 ) ? match[ 1 ] : null ;
}

/* Bootstrap components */
jQuery( ".dropdown-menu li a" ).click(function() {
	jQuery(this).parents( ".input-group-btn" ).find( '.btn' ).html( $(this).text() + " <span class='caret'></span>" );
	jQuery(this).parents( ".input-group-btn" ).find( 'input' ).val( $(this).data( 'value' ) );
});
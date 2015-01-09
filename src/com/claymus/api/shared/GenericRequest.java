package com.claymus.api.shared;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Validate;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.gwt.regexp.shared.RegExp;


@SuppressWarnings("serial")
public class GenericRequest implements Serializable {

	private static final Logger logger =
			Logger.getLogger( GenericApi.class.getName() );

	
	protected static final String REGEX_NUMBER = "^[0-9]+$";
	protected static final String REGEX_URI = "^(/[A-Za-z0-9-]+)+$";
	protected static final String REGEX_EMAIL = "^[A-Za-z0-9]+([._+-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,4}$";

	
	private String accessToken;


	@SuppressWarnings("unused")
	private GenericRequest() {}

	public GenericRequest( String accessToken ) {
		this.accessToken = accessToken;
	}

	
	public final String getAccessToken() {
		return accessToken;
	}

	
	public final void validate() throws InvalidArgumentException, UnexpectedServerException {
		for( Field field : this.getClass().getDeclaredFields() ) {
			Validate validate = field.getAnnotation( Validate.class );
			if( validate != null ) {
				field.setAccessible( true );
				try {
					Object value = field.get( this );
					if( validate.required() && value == null ) {
						throw new InvalidArgumentException( "'" + field.getName() + "' is missing." );
						
					} else if( !validate.regEx().isEmpty() && field.getType() == String.class && value != null ) {
						RegExp regEx = RegExp.compile( validate.regEx() );
						if( regEx.exec( (String) value ) == null )
							throw new InvalidArgumentException( "'" + field.getName() + "' value is invalid." );
					}
				} catch( IllegalAccessException e ) {
					logger.log( Level.SEVERE, "Unexpected exception occured !", e );
					throw new UnexpectedServerException();
				}
			}
		}
		
	}

}

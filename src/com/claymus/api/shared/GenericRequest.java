package com.claymus.api.shared;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
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

	
	protected static final String REGEX_NON_EMPTY_STRING = "^(\\s*\\S+\\s*)+$";
	protected static final String REGEX_NUMBER = "^[0-9]+$";
	protected static final String REGEX_URI = "^(/[A-Za-z0-9-]+)+$";
	protected static final String REGEX_EMAIL = "^[A-Za-z0-9]+([._+-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,4}$";

	
	public GenericRequest() {}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final void validate() throws InvalidArgumentException, UnexpectedServerException {
		for( Field field : this.getClass().getDeclaredFields() ) {
			Validate validate = field.getAnnotation( Validate.class );
			if( validate != null ) {
				field.setAccessible( true );
				try {
					Object value = field.get( this );
					if( value == null ) {

						if( validate.required() )
							throw new InvalidArgumentException( "'" + field.getName() + "' is missing." );
					
					} else if( value != null ) {
						
						if( field.getType() == String.class ) {
							if( !validate.regEx().isEmpty() && RegExp.compile( validate.regEx() ).exec( (String) value ) == null )
								throw new InvalidArgumentException( "'" + field.getName() + "' value is invalid." );
	
						} else if( field.getType() == Long.class ) {
							if( (Long) value < validate.minLong() )
								throw new InvalidArgumentException( "'" + field.getName() + "' must be greater than or equal to " + validate.minLong() );
						
						} else if( field.getType() == List.class && GenericRequest.class.isAssignableFrom( (Class) ( (ParameterizedType) field.getGenericType() ).getActualTypeArguments()[0] ) ) {
							for( GenericRequest request : (List<GenericRequest>) value )
								request.validate();
						}
					}
				} catch( IllegalAccessException e ) {
					logger.log( Level.SEVERE, "Unexpected exception occured !", e );
					throw new UnexpectedServerException();
				}
			}
		}
		
	}

}

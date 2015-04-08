package com.claymus.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Validate {
	
	public boolean required() default false;
	
	public String regEx() default "";

	public long minLong() default Long.MIN_VALUE;

}

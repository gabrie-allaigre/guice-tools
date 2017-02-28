package com.talanlabs.guicetools.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface InjectLogger {

	/**
	 * Logger name, default is class name
	 */
	String value() default "";

	/**
	 * If true, name is absolute else use class name + name
	 */
	boolean absolute() default false;

}

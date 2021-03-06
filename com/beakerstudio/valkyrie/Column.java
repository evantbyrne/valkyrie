package com.beakerstudio.valkyrie;

import java.lang.annotation.*; 

/**
 * Column Annotation
 * @author Evan Byrne
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	boolean primary() default false;
	String type() default "";
	String field() default "";
	String middleman() default "";
	
}
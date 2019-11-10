/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Expected {
	/**
	 * Returns the modifier.
	 * @return the modifier
	 */
	Modifier value();
}

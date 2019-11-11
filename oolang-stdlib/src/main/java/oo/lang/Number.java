/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.lang;

import oo.internal.Expected;
import oo.internal.Modifier;
import oo.io.Serializable;

import javax.annotation.Nonnull;

/**
 * Superclass for all platform classes representing numeric values.
 */
@Expected(Modifier.ABSTRACT)
public abstract class Number extends Object implements Serializable {

	/**
	 * Constructor for subclasses to call.
	 */
	public Number() { }

	/**
	 * Returns the value of the specified number as an {@code Integer}.
	 *
	 * @return  the numeric value represented by this object after conversion
	 *          to type {@code Integer}.
	 */
	@Nonnull
	@Expected(Modifier.ABSTRACT)
	public abstract Integer intValue();
}

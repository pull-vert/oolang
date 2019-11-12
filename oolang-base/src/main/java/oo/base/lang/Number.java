/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.base.lang;

import oo.lang.Expected;
import oo.lang.Modifier;
import oo.base.io.Serializable;
import oo.lang.NotNull;

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
	@NotNull
	@Expected(Modifier.ABSTRACT)
	public abstract Integer intValue();
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.base.lang;

import oo.lang.Expected;
import oo.lang.Modifier;
import oo.base.io.Serializable;
import oo.lang.NotNull;

/**
 * Represents a value which is either `true` or `false`.
 * Non-nullable values of this type are represented as values of the primitive type `boolean`.
 */
@Expected(Modifier.FINAL)
public abstract class Boolean extends Object implements Serializable, Comparable<Boolean> {

	private Boolean() { }

	/**
	 * Compares this {@code Boolean} instance with another.
	 *
	 * @param   b the {@code Boolean} instance to be compared
	 * @return  zero if this object represents the same boolean value as the
	 *          argument; a positive value if this object represents true
	 *          and the argument represents false; and a negative value if
	 *          this object represents false and the argument represents true
	 * @see     Comparable
	 */
	@Override
	@NotNull
	@Expected(Modifier.FINAL)
	public abstract Integer compareTo(@NotNull Boolean b);
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.base.lang;

import oo.lang.Expected;
import oo.lang.Modifier;
import oo.lang.NotNull;

@Expected(Modifier.FINAL)
public abstract class Integer extends Number implements Comparable<Integer> {

	private Integer() { }

	@NotNull
	@Expected(Modifier.FINAL)
	public static Integer MIN_VALUE;

	@NotNull
	@Expected(Modifier.FINAL)
	public static Integer MAX_VALUE;

	@Override
	@NotNull
	@Expected(Modifier.FINAL)
	public abstract Integer intValue();

	/**
	 * Compares two {@code Integer} objects numerically.
	 *
	 * @param   anotherInteger   the {@code Integer} to be compared.
	 * @return  the value {@code 0} if this {@code Integer} is
	 *          equal to the argument {@code Integer}; a value less than
	 *          {@code 0} if this {@code Integer} is numerically less
	 *          than the argument {@code Integer}; and a value greater
	 *          than {@code 0} if this {@code Integer} is numerically
	 *           greater than the argument {@code Integer} (signed
	 *           comparison).
	 */
	@Override
	@NotNull
	@Expected(Modifier.FINAL)
	public abstract Integer compareTo(@NotNull Integer anotherInteger);
}

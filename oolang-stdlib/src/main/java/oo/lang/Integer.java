/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.lang;

import oo.internal.Expected;
import oo.internal.Modifier;

import javax.annotation.Nonnull;

@Expected(Modifier.FINAL)
public abstract class Integer extends Number implements Comparable<Integer> {

	private Integer() { }

	@Nonnull
	@Expected(Modifier.FINAL)
	public static Integer MIN_VALUE;

	@Nonnull
	@Expected(Modifier.FINAL)
	public static Integer MAX_VALUE;

	@Override
	@Nonnull
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
	@Nonnull
	@Expected(Modifier.FINAL)
	public abstract Integer compareTo(@Nonnull Integer anotherInteger);
}

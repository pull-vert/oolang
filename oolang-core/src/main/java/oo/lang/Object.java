/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.lang;

import oo.IObject;
import oo.internal.Expected;
import oo.internal.Modifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The root of the Oolang class hierarchy. Every Oolang class has {@link Object} as a superclass.
 */
@Expected(Modifier.OPEN)
public abstract class Object implements IObject {

	/**
	 * Constructs a new object.
	 */
	public Object() {}

	@Override
	@Nonnull
	@Expected(Modifier.OPEN)
	public abstract Integer hashCode_();

	@Override
	@Nonnull
	@Expected(Modifier.OPEN)
	public abstract Boolean equals(@Nullable Object obj);

	@Override
	@Nonnull
	@Expected(Modifier.OPEN)
	public abstract String toString_();
}

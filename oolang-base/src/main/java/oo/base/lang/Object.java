/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.base.lang;

import oo.base.IObject;
import oo.lang.Expected;
import oo.lang.Modifier;
import oo.lang.NotNull;
import oo.lang.Nullable;

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
	@NotNull
	@Expected(Modifier.OPEN)
	public abstract Integer hashCode_();

	@Override
	@NotNull
	@Expected(Modifier.OPEN)
	public abstract Boolean equals(@Nullable Object obj);

	@Override
	@NotNull
	@Expected(Modifier.OPEN)
	public abstract String toString_();
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.lang;

import oo.IObject;
import oo.internal.Expected;
import oo.internal.Modifier;

/**
 * The root of the Oolang class hierarchy. Every Oolang class has {@link Object} as a superclass.
 */
@Expected(Modifier.OPEN)
public abstract class Object implements IObject {

}

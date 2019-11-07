/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.lang

import oo.internal.Expected
import oo.internal.Modifier

/**
 * The root of the Oolang class hierarchy. Every Oolang class has [Object] as a superclass.
 */
@Expected(Modifier.OPEN)
abstract class Object : IObject {
    @Expected(Modifier.OPEN)
    abstract override fun equals(other: Object?): Boolean

    @Expected(Modifier.OPEN)
    abstract override fun hashCode(): Int

    // todo replace with real name "toString" when in oolang file
    @Expected(Modifier.OPEN)
    abstract override fun toString_(): String
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.lang

import oo.internal.Expected
import oo.internal.Modifier
import oo.io.Serializable

/**
 * Represents a value which is either `true` or `false`.
 * Non-nullable values of this type are represented as values of the primitive type `boolean`.
 */
@Expected(Modifier.FINAL)
abstract class Boolean private constructor() : Object(), Serializable, Comparable<Boolean> {
    /**
     * Returns the inverse of this boolean.
     */
    @Expected(Modifier.FINAL)
    abstract operator fun not(): Boolean

    /**
     * Performs a logical `and` operation between this Boolean and the [other] one. Unlike the `&&` operator,
     * this function does not perform short-circuit evaluation. Both `this` and [other] will always be evaluated.
     */
    @Expected(Modifier.FINAL)
    abstract infix fun and(other: Boolean): Boolean

    /**
     * Performs a logical `or` operation between this Boolean and the [other] one. Unlike the `||` operator,
     * this function does not perform short-circuit evaluation. Both `this` and [other] will always be evaluated.
     */
    @Expected(Modifier.FINAL)
    abstract infix fun or(other: Boolean): Boolean

    /**
     * Performs a logical `xor` operation between this Boolean and the [other] one.
     */
    @Expected(Modifier.FINAL)
    abstract infix fun xor(other: Boolean): Boolean

    @Expected(Modifier.FINAL)
    abstract override fun compareTo(other: Boolean): Int
}

package oo.lang

import oo.internal.Expected
import oo.internal.Modifier

/**
 * Classes which inherit from this interface have a defined total ordering between their instances.
 */
@Expected(Modifier.ABSTRACT)
interface Comparable<in T> : IObject {
    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    operator fun compareTo(other: T): Int
}

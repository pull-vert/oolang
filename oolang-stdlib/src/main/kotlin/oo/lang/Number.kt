package oo.lang

import oo.internal.Expected
import oo.internal.Modifier
import oo.io.Serializable

/**
 * Superclass for all platform classes representing numeric values.
 */
@Expected(Modifier.ABSTRACT)
abstract class Number : Object(), Serializable {

    /**
     * Returns the value of this number as an [Integer], which may involve rounding or truncation.
     */
    @Expected(Modifier.ABSTRACT)
    abstract fun intValue(): Integer

    /**
     * Returns the value of this number as a [Long], which may involve rounding or truncation.
     */
    @Expected(Modifier.ABSTRACT)
    abstract fun longValue(): Long

    /**
     * Returns the value of this number as a [Float], which may involve rounding.
     */
    @Expected(Modifier.ABSTRACT)
    abstract fun floatValue(): Float

    /**
     * Returns the value of this number as a [Double], which may involve rounding.
     */
    @Expected(Modifier.ABSTRACT)
    abstract fun doubleValue(): Double

    /**
     * Returns the value of this number as a [Byte], which may involve rounding or truncation.
     */
    @Expected(Modifier.OPEN)
    abstract fun byteValue(): Byte

    /**
     * Returns the value of this number as a [Short], which may involve rounding or truncation.
     */
    @Expected(Modifier.OPEN)
    abstract fun shortValue(): Short
}

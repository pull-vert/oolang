package oo.lang

import kotlin.Comparable
import kotlin.Int

/**
 * Represents a 32-bit signed integer.
 * On the JVM, non-nullable values of this type are represented as values of the primitive type `int`.
 */
public class Integer private constructor() : Number(), Comparable<Int> {

}

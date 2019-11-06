package oo.lang

/**
 * The root of the Oolang class hierarchy. Every Oolang class has [Object] as a superclass.
 */
abstract class Object : Any(), IObject {
    abstract override fun equals(other: Object?): Boolean
}

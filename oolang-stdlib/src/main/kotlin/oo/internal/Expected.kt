/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.internal

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Expected(
        val modifier: Modifier
)

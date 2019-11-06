package oo.internal

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Expected(
        val modifier: Modifier,
        val jvmName: String = ""
)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import org.jspecify.annotations.NonNull;

/**
 * Lists the JVM invocation instructions.
 */
public final class JvmInvocationUtils {
    // un-instantiable
    private JvmInvocationUtils() {
    }

    // field access instructions

    public static final @NonNull String GET_STATIC = "getStatic";
    public static final @NonNull String PUT_STATIC = "putStatic";
    public static final @NonNull String GET_FIELD = "getField";
    public static final @NonNull String PUT_FIELD = "putField";


    // function invocation instructions

    /**
     * For static functions.
     */
    public static final @NonNull String INVOKE_STATIC = "invokeStatic";
    /**
     * For non-private, non-static, non-constructor instance functions.
     */
    public static final @NonNull String INVOKE_VIRTUAL = "invokeVirtual";
    /**
     * For private functions, superclass function calls, constructors.
     */
    public static final @NonNull String INVOKE_SPECIAL = "invokeSpecial";
    /**
     * For interface functions (similar to {@link #INVOKE_VIRTUAL} but for interfaces).
     */
    public static final @NonNull String INVOKE_INTERFACE = "invokeInterface";
    /**
     * For lambda and dynamic expressions.
     */
    public static final @NonNull String INVOKE_DYNAMIC = "invokeDynamic";
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identify an operation that allow specific syntax (eg : {@code Boolean equals(Object?)} will be callable with {@code ==})
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Operator { }

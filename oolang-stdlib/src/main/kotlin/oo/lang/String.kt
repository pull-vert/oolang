/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.lang

import oo.internal.Expected
import oo.internal.Modifier
import oo.io.Serializable

@Expected(Modifier.FINAL)
abstract class String private constructor() : Object(), Serializable, Comparable<String> {
}

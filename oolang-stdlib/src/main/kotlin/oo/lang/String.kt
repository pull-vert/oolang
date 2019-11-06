package oo.lang

import oo.internal.Expected
import oo.internal.Modifier
import oo.io.Serializable

@Expected(Modifier.FINAL)
abstract class String : Object(), Serializable, Comparable<String> {
}

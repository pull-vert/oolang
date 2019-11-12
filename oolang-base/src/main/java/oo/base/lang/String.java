/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.base.lang;

import oo.lang.Expected;
import oo.lang.Modifier;
import oo.base.io.Serializable;
import oo.lang.NotNull;

@Expected(Modifier.FINAL)
public abstract class String extends Object implements Serializable, Comparable<String> {

	private String() { }

	/**
	 * Compares two strings lexicographically.
	 * The comparison is based on the Unicode value of each character in
	 * the strings. The character sequence represented by this
	 * {@code String} object is compared lexicographically to the
	 * character sequence represented by the argument string. The result is
	 * a negative integer if this {@code String} object
	 * lexicographically precedes the argument string. The result is a
	 * positive integer if this {@code String} object lexicographically
	 * follows the argument string. The result is zero if the strings
	 * are equal; {@code compareTo} returns {@code 0} exactly when
	 * the {@link #equals(Object)} method would return {@code true}.
	 * <p>
	 * This is the definition of lexicographic ordering. If two strings are
	 * different, then either they have different characters at some index
	 * that is a valid index for both strings, or their lengths are different,
	 * or both. If they have different characters at one or more index
	 * positions, let <i>k</i> be the smallest such index; then the string
	 * whose character at position <i>k</i> has the smaller value, as
	 * determined by using the {@code <} operator, lexicographically precedes the
	 * other string. In this case, {@code compareTo} returns the
	 * difference of the two character values at position {@code k} in
	 * the two string -- that is, the value:
	 * <blockquote><pre>
	 * this.charAt(k)-anotherString.charAt(k)
	 * </pre></blockquote>
	 * If there is no index position at which they differ, then the shorter
	 * string lexicographically precedes the longer string. In this case,
	 * {@code compareTo} returns the difference of the lengths of the
	 * strings -- that is, the value:
	 * <blockquote><pre>
	 * this.length()-anotherString.length()
	 * </pre></blockquote>
	 *
	 * <p>For finer-grained String comparison, refer to
	 * {@link java.text.Collator}.
	 *
	 * @param   anotherString   the {@code String} to be compared.
	 * @return  the value {@code 0} if the argument string is equal to
	 *          this string; a value less than {@code 0} if this string
	 *          is lexicographically less than the string argument; and a
	 *          value greater than {@code 0} if this string is
	 *          lexicographically greater than the string argument.
	 */
	@Override
	@NotNull
	@Expected(Modifier.FINAL)
	public abstract Integer compareTo(@NotNull String anotherString);
}

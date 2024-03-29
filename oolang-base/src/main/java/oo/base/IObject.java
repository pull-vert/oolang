/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.base;

import oo.lang.NotNull;
import oo.lang.Nullable;
import oo.lang.Operator;
import oo.base.lang.Boolean;
import oo.base.lang.Integer;
import oo.base.lang.Object;
import oo.base.lang.String;

/**
 * The root of the Oolang interface hierarchy. Every Oolang interface has {@link IObject} as a superinterface.
 * <p>{@link IObject} is virtual, it is not present in any Oolang interface corresponding .class file
 */
public interface IObject {

	/**
	 * todo update javadoc links with oolang ones
	 * Returns a hash code value for the object. This method is
	 * supported for the benefit of hash tables such as those provided by
	 * {@link java.util.HashMap}.
	 * <p>
	 * The general contract of {@code hashCode} is:
	 * <ul>
	 * <li>Whenever it is invoked on the same object more than once during
	 *     an execution of a Java application, the {@code hashCode} method
	 *     must consistently return the same integer, provided no information
	 *     used in {@code equals} comparisons on the object is modified.
	 *     This integer need not remain consistent from one execution of an
	 *     application to another execution of the same application.
	 * <li>If two objects are equal according to the {@code equals(Object)}
	 *     method, then calling the {@code hashCode} method on each of
	 *     the two objects must produce the same integer result.
	 * <li>It is <em>not</em> required that if two objects are unequal
	 *     according to the {@link oo.base.lang.Object#equals(oo.base.lang.Object)}
	 *     method, then calling the {@code hashCode} method on each of the
	 *     two objects must produce distinct integer results.  However, the
	 *     programmer should be aware that producing distinct integer results
	 *     for unequal objects may improve the performance of hash tables.
	 * </ul>
	 *
	 * @implSpec
	 * As far as is reasonably practical, the {@code hashCode} method defined
	 * by class {@code Object} returns distinct integers for distinct objects.
	 *
	 * @return  a hash code value for this object.
	 * @see     oo.base.lang.Object#equals(oo.base.lang.Object)
	 * @see     java.lang.System#identityHashCode
	 */
	@NotNull
	Integer hashCode_();

	/**
	 * todo update javadoc links with oolang ones
	 * Indicates whether some other object is "equal to" this one.
	 * <p>
	 * The {@code equals} method implements an equivalence relation
	 * on non-null object references:
	 * <ul>
	 * <li>It is <i>reflexive</i>: for any non-null reference value
	 *     {@code x}, {@code x.equals(x)} should return
	 *     {@code true}.
	 * <li>It is <i>symmetric</i>: for any non-null reference values
	 *     {@code x} and {@code y}, {@code x.equals(y)}
	 *     should return {@code true} if and only if
	 *     {@code y.equals(x)} returns {@code true}.
	 * <li>It is <i>transitive</i>: for any non-null reference values
	 *     {@code x}, {@code y}, and {@code z}, if
	 *     {@code x.equals(y)} returns {@code true} and
	 *     {@code y.equals(z)} returns {@code true}, then
	 *     {@code x.equals(z)} should return {@code true}.
	 * <li>It is <i>consistent</i>: for any non-null reference values
	 *     {@code x} and {@code y}, multiple invocations of
	 *     {@code x.equals(y)} consistently return {@code true}
	 *     or consistently return {@code false}, provided no
	 *     information used in {@code equals} comparisons on the
	 *     objects is modified.
	 * <li>For any non-null reference value {@code x},
	 *     {@code x.equals(null)} should return {@code false}.
	 * </ul>
	 * <p>
	 * The {@code equals} method for class {@code Object} implements
	 * the most discriminating possible equivalence relation on objects;
	 * that is, for any non-null reference values {@code x} and
	 * {@code y}, this method returns {@code true} if and only
	 * if {@code x} and {@code y} refer to the same object
	 * ({@code x == y} has the value {@code true}).
	 * <p>
	 * Note that it is generally necessary to override the {@code hashCode}
	 * method whenever this method is overridden, so as to maintain the
	 * general contract for the {@code hashCode} method, which states
	 * that equal objects must have equal hash codes.
	 *
	 * @param   obj   the reference object with which to compare.
	 * @return  {@code true} if this object is the same as the obj
	 *          argument; {@code false} otherwise.
	 * @see     oo.base.lang.Object#hashCode_()
	 * @see     java.util.HashMap
	 */
	@Operator
	@NotNull
	Boolean equals(@Nullable Object obj);

	/**
	 * Returns a string representation of the object. In general, the
	 * {@code toString} method returns a string that
	 * "textually represents" this object. The result should
	 * be a concise but informative representation that is easy for a
	 * person to read.
	 * It is recommended that all subclasses override this method.
	 * <p>
	 * The {@code toString} method for class {@code Object}
	 * returns a string consisting of the name of the class of which the
	 * object is an instance, the at-sign character `{@code @}', and
	 * the unsigned hexadecimal representation of the hash code of the
	 * object. In other words, this method returns a string equal to the
	 * value of:
	 * <blockquote>
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre></blockquote>
	 *
	 * @return  a string representation of the object.
	 */
	@NotNull
	String toString_();
}

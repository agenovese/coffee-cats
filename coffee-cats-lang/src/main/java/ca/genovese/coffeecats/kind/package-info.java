/**
 * This package contains Interfaces allowing the partial
 * representation of parametric polymorphic types in Java.
 *
 * <p>For any KindN the first type argument is the paramaterized
 * type, the following N arguments are the type's arguments.
 *
 * <p>For example, this allows us to represent Functor as:
 * <pre>
 * <code>
 * interface {@code Functor<F>} {
 *   {@code <A, B>} {@code Kind<F, B>} map({@code Kind<F, A>} fa, {@code Function<A, B>} f);
 * }
 * </code>
 * </pre>
 *
 * <p>Which would otherwise be represented as something like the
 * following in a Java like language that allowed parametric
 * polymorphism
 * <pre>
 * <code>
 * interface Functor<F> {
 *   <A, B> F<B> map(F<A> fa, Function<A, B> f);
 * }
 * </code>
 * </pre>
 */
package ca.genovese.coffeecats.kind;


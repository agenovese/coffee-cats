/**
 * Eval is a monad which controls evaluation.
 * <p>This type wraps a value (or a computation that produces a value)
 * and can produce it on command via the `.value()` method.
 * <p>There are three basic evaluation strategies:
 * <ul>
 * <li>Now:    evaluated immediately</li>
 * <li>- Later:  evaluated once when value is needed</li>
 * <li>- Always: evaluated every time value is needed</li>
 * </ul>
 * <p>The Later and Always are both lazy strategies while Now is eager.
 * Later and Always are distinguished from each other only by
 * memoization: once evaluated Later will save the value to be returned
 * immediately if it is needed again. Always will run its computation
 * every time.
 * <p>Eval supports stack-safe lazy computation via the .map and .flatMap
 * methods, which use an internal trampoline to avoid stack overflows.
 * Computation done within .map and .flatMap is always done lazily,
 * even when applied to a Now instance.
 * <p>Use .map and .flatMap to chain computation, and use .value
 * to get the result when needed. It is not good style to create
 * Eval instances whose computation involves calling .value on another
 * Eval instance -- this can defeat the trampolining and lead to stack
 * overflows.
 */
package ca.genovese.coffeecats.data.eval;

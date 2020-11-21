package main

/**
 * An exception that indicates that the iteration of a function was interrupted
 * before it could reach its expected or hypothesized goal, <em>and</em> that a
 * list of values prior to the iteration is available.
 * @param msg A message that is passed on to the `RuntimeException`
 *            constructor.
 * @param cause The exception that interrupted the iteration. Should generally
 *              be an `ArithmeticException`.
 * @param partial A list of values obtained prior to the interruption. If
 *                nothing else, it should contain the very first input for the
 *                iterated function. For example, for the classic Collatz
 *                function starting with 1431655770, the list would consist of
 *                1431655770 and 715827885. The list wouldn't include
 *                2147483656, which is just slightly beyond the range of `Int`
 *                to represent. (Note: the iteration does eventually reach a
 *                power of 2, namely 16, and from there quickly drops to 1).
 * @param fn The function that had its iteration interrupted. For example, the
 *           classic Collatz function 3<i>n</i> + 1 if <i>n</i> is odd, half
 *           <i>n</i> if <i>n</i> is even.
 * @throws IllegalArgumentException If `partial` is empty.
 */
class InterruptedIterationException(msg: String, cause: Throwable,
                                    val partial: List[Int], val fn: Int => Int)
  extends RuntimeException(msg, cause) {
  if (partial.isEmpty) {
    val msg = "Partial list shouldn't be empty"
    throw new IllegalArgumentException(msg)
  }

}

package main

/**
 * A collection of functions that can be used with [[main.IntegerTree]]. Each of
 * the included functions will thrown an `ArithmeticException` in the case of
 * integer overflow.
 */
object CollatzFunctions {

  /**
   * The classic Collatz function.
   * @param n An integer. For example, 7.
   * @return Half `n` if `n` is even, thrice `n` plus 1 if `n` is odd. For
   *         example, given 7, this gives 22. Putting that result back into the
   *         function gives 11.
   * @throws ArithmeticException If `n` is an odd integer greater than
   *                             715827882 or less than &minus;715827882. It
   *                             should not occur for any even integer.
   */
  def classic(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(3, n), 1)
  }

  /**
   * A variant of the classic Collatz function, 3<i>n</i> &minus; 1. The
   * difference from the classic Collatz function is one of signs. The Collatz
   * conjecture can be stated as that, given negative <i>n</i>, iterating this
   * variant always leads to &minus;1. This has yet to be proven.
   * @param n An integer. For example, 7.
   * @return Half `n` if `n` is even, thrice `n` minus 1 if `n` is odd. For
   *         example, given 7, this gives 20. Putting that result back into
   *         the function gives 10.
   * @throws ArithmeticException If `n` is an odd integer greater than
   *                             715827882 or less than &minus;715827882. It
   *                             should not occur for any even integer.
   */
  def threeXMinus1(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(3, n), -1)
  }

  /**
   * A variant of the classic Collatz function, 5<i>n</i> + 1. It is well known
   * that there are infinitely many values of <i>n</i> for which the iteration
   * of this function never reaches &minus;1 nor 1, such as 13.
   * @param n An integer. For example, 7.
   * @return Half `n` if `n` is even, five times `n` plus 1 if `n` is odd. For
   *         example, given 7, this gives 36. Putting that result back into the
   *         function gives 18.
   * @throws ArithmeticException If `n` is an odd integer greater than
   *                             429496730 or less than &minus;429496730. It
   *                             should not occur for any even integer.
   */
  def fiveXPlus1(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(5, n), 1)
  }

  /**
   * A variant of the classic Collatz function, &minus;3<i>n</i> + 1. I
   * conjecture that iterating this function always reaches 1 or 13 for any
   * nonzero value of <i>n</i>. This has yet to be proven.
   * @param n An integer. For example, 7.
   * @return Half `n` if `n` is even, thrice `n` minus 1 if `n` is odd. For
   *         example, given 7, this gives &minus;20. Putting that result back
   *         into the function gives &minus;10.
   * @throws ArithmeticException If `n` is an odd integer greater than
   *                             715827882 or less than &minus;715827882. It
   *                             should not occur for any even integer.
   */
  def negCollatz(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(-3, n), 1)
  }

  /**
   * This is perhaps the most predictable variant of the Collatz function,
   * <i>n</i> + 1. It is easily proven that iterating this function for any
   * integer eventually reaches either 0 or 1.
   * @param n An integer. For example, 7.
   * @return <i>n</i> + 1 if <i>n</i> is odd, half <i>n</i> if <i>n</i> is
   *         even. For example, for 7, this function would return 8, and for 8
   *         it would return 4.
   * @throws ArithmeticException If and only if `n` equals `Int.MaxValue`. No
   *                             other `Int` should cause this exception. The
   *                             exception message will read "Result 2147483648
   *                             is outside the range of int".
   */
  def oneXPlus1(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => if (n == Int.MaxValue) {
        val msg = "Result " + (n.toLong + 1).toString +
          " is outside the range of int"
        throw new ArithmeticException(msg)
      }
      n + 1
  }

}

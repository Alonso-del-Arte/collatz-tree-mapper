package main

object CollatzFunctions {

  def classic(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(3, n), 1)
  }

  def threeXMinus1(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(3, n), -1)
  }

  def fiveXPlus1(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(5, n), 1)
  }

  def negCollatz(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => Math.addExact(Math.multiplyExact(-3, n), 1)
  }

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

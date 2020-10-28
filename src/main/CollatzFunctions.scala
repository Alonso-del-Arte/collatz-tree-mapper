package main

object CollatzFunctions {

  def classic(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => 3 * n + 1
  }

  def threeXMinus1(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => 3 * n - 1
  }

  def fiveXPlus1(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => 3 * n + 1
  }

  def negCollatz(n: Int): Int = n % 2 match {
    case 0 => n / 2
    case _ => -3 * n + 1
  }

}

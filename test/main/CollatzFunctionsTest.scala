package main

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._

class CollatzFunctionsTest {

  @Test def testCollatz(): Unit = {
    val source = LazyList.iterate(52)(CollatzFunctions.classic)
    val expected = List(52, 26, 13, 40, 20, 10, 5, 16, 8, 4, 2, 1)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

  // TODO: Figure out more idiomatic way to do this test
  @Test def testCollatzOverflowDetection(): Unit = {
    val number = 1431655765
    try {
      val result = CollatzFunctions.classic(number)
      val msg = "Applying Collatz function to " + number +
        " should have detected overflow, not given result " + result
      fail(msg)
    } catch {
      case ae: ArithmeticException => println("Overflow detected for " + number
        + " correctly caused ArithmeticException")
        println("\"" + ae.getMessage + "\"")
      case e: Exception => val msg = e.getClass.getName +
        " is the wrong exception to throw for overflow with " + number
        fail(msg)
    }
  }

  @Test def testCollatzVarMinus1(): Unit = {
    val source = LazyList.iterate(52)(CollatzFunctions.threeXMinus1)
    val expected = List(52, 26, 13, 38, 19, 56, 28, 14, 7, 20, 10, 5)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

  // TODO: Figure out more idiomatic way to do this test
  @Test def testCollatzVarMinus1OverflowDetection(): Unit = {
    val number = 1431655765
    try {
      val result = CollatzFunctions.threeXMinus1(number)
      val msg = "Applying 3x - 1 function to " + number +
        " should have detected overflow, not given result " + result
      fail(msg)
    } catch {
      case ae: ArithmeticException => println("Overflow detected for " + number
        + " correctly caused ArithmeticException")
        println("\"" + ae.getMessage + "\"")
      case e: Exception => val msg = e.getClass.getName +
        " is the wrong exception to throw for overflow with " + number
        fail(msg)
    }
  }

  @Test def testFiveXPlus1(): Unit = {
    val source = LazyList.iterate(7)(CollatzFunctions.fiveXPlus1)
    val expected = List(7, 36, 18, 9, 46, 23, 116, 58, 29, 146, 73, 366)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

  // TODO: Figure out more idiomatic way to do this test
  @Test def testFiveXPlus1OverflowDetection(): Unit = {
    val number = 429496731
    try {
      val result = CollatzFunctions.fiveXPlus1(number)
      val msg = "Applying 5x + 1 function to " + number +
        " should have detected overflow, not given result " + result
      fail(msg)
    } catch {
      case ae: ArithmeticException => println("Overflow detected for " + number
        + " correctly caused ArithmeticException")
        println("\"" + ae.getMessage + "\"")
      case e: Exception => val msg = e.getClass.getName +
        " is the wrong exception to throw for overflow with " + number
        fail(msg)
    }
  }

  @Test def testNegCollatz(): Unit = {
    val source = LazyList.iterate(52)(CollatzFunctions.negCollatz)
    val expected = List(52, 26, 13, -38, -19, 58, 29, -86, -43, 130, 65, -194)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

  // TODO: Figure out more idiomatic way to do this test
  @Test def testNegCollatzOverflowDetection(): Unit = {
    val number = 715827885
    try {
      val result = CollatzFunctions.negCollatz(number)
      val msg = "Applying -3x + 1 function to " + number +
        " should have detected overflow, not given result " + result
      fail(msg)
    } catch {
      case ae: ArithmeticException => println("Overflow detected for " + number
        + " correctly caused ArithmeticException")
        println("\"" + ae.getMessage + "\"")
      case e: Exception => val msg = e.getClass.getName +
        " is the wrong exception to throw for overflow with " + number
        fail(msg)
    }
  }

  @Test def testOneXPlus1(): Unit = {
    val source = LazyList.iterate(108)(CollatzFunctions.oneXPlus1)
    val expected = List(108, 54, 27, 28, 14, 7, 8, 4, 2, 1, 2, 1)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

  // TODO: Figure out more idiomatic way to do this test
  @Test def testOneXPlus1OverflowDetection(): Unit = {
    val number = Int.MaxValue
    try {
      val result = CollatzFunctions.oneXPlus1(number)
      val msg = "Applying x + 1 function to " + number +
        " should have detected overflow, not given result " + result
      fail(msg)
    } catch {
      case ae: ArithmeticException => println("Overflow detected for " + number
        + " correctly caused ArithmeticException")
        println("\"" + ae.getMessage + "\"")
      case e: Exception => val msg = e.getClass.getName +
        " is the wrong exception to throw for overflow with " + number
        fail(msg)
    }
  }

}

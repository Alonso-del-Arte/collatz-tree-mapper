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

  @Test def testCollatzVarMinus1(): Unit = {
    val source = LazyList.iterate(52)(CollatzFunctions.threeXMinus1)
    val expected = List(52, 26, 13, 38, 19, 56, 28, 14, 7, 20, 10, 5)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

  @Test def testFiveXPlsu1(): Unit = {
    val source = LazyList.iterate(7)(CollatzFunctions.fiveXPlus1)
    val expected = List(7, 36, 18, 9, 46, 23, 116, 58, 29, 146, 73, 366)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

  @Test def testNegCollatz(): Unit = {
    val source = LazyList.iterate(52)(CollatzFunctions.negCollatz)
    val expected = List(52, 26, 13, -38, -19, 58, 29, -86, -43, 130, 65, -194)
    val actual = source.take(12).toList
    assertEquals(expected, actual)
  }

}

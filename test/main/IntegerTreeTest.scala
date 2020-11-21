package main

import scala.util.Random

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._

object IntegerTreeTest {

  /**
   * This is a variant of the Collatz function designed to overflow quickly
   * under certain circumstances. This overly complicated function is provided
   * for testing purposes only, and should never be made available in a
   * production context.
   * @param n An integer. For example, 7.
   * @return Half `n` if `n` is even, `n` times 127 is `n` is a positive odd
   *         multiple of 7, `n` times 64 if `n` is one more than a positive
   *         even multiple of 7, `n` times 31 if `n` is 2 plus a negative odd
   *         multiple of 7, and thrice `n` plus 1 in all other cases. For
   *         example, given 7, this function would return 127 &times; 7 = 889.
   *         Only multiplication by 127 uses `Math.multiplyExact()`, so other
   *         cases involving odd numbers overflowing will definitely give
   *         incorrect results.
   * @throws ArithmeticException If `n` is an odd multiple of 7 greater than
   *                             16909326.
   */
  def collatzVariantQO(n: Int): Int = (n % 2, n % 7) match {
    case (0, _) => n / 2
    case (_, 0) => Math.multiplyExact(n, 127)
    case (_, 1) => 64 * n
    case (_, -5) => 31 * n
    case _ => 3 * n + 1
  }

}

class IntegerTreeTest {

  @Test def testGivenFunctionMatches(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.negCollatz)
    val random = new Random()
    val number = 2 * random.nextInt(256) + 1
    val expected = CollatzFunctions.negCollatz(number)
    val actual = tree.successor(number)
    assertEquals(expected, actual)
  }

  /**
   * Test of successor function of class IntegerTree.
   */
  @Test def testSuccessor(): Unit = {
    println("successor")
    val tree = new IntegerTree(CollatzFunctions.classic)
    val expected = 8
    val actual = tree.successor(16)
    assertEquals(expected, actual)
  }

  /**
   * Test of precursors function of class IntegerTree.
   */
  @Test def testPrecursors(): Unit = {
    println("precursors")
    val tree = new IntegerTree(CollatzFunctions.classic)
    assert(tree.successor(128) == 64)
    assert(tree.successor(21) == 64)
    val expected = Set(21, 128)
    val actual = tree.precursors(64)
    assertEquals(expected, actual)
  }

  /**
   * Another test of precursors function of class IntegerTree. If there haven't
   * been any queries for a particular successor, nor has that number been
   * encountered along any previously traversed path, the list of precursors
   * should be empty.
   */
  @Test def testPrecursorsUnqueried(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.classic)
    val expected = Set()
    val actual = tree.precursors(64)
    assertEquals(expected, actual)
  }

  /**
   * Another test of precursors function of class IntegerTree. If there haven't
   * been any queries for a particular successor, nor has that number been
   * encountered along any previously traversed path, the list of precursors
   * should be empty... except when those precursors are on a reasonable short
   * cycle leading to 1.
   */
  @Test def testPrecursorsPertainingTo1(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.classic)
    val expected = Set(4)
    val actual = tree.precursors(2)
    assertEquals(expected, actual)
  }

  @Test def testPathPertainingTo1(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.classic)
    val option = tree.path(4)
    if (option.isEmpty) {
      val msg = "Path query for 4 with Collatz function should have given result"
      fail(msg)
    } else {
      val expected = List(4, 2, 1)
      val actual = option.get
      assertEquals(expected, actual)
    }
  }

  @Test def testPathUnqueried(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.classic)
    val option = tree.path(5)
    val msg = "Unqueried path from 5 to 1 should come up empty"
    assert(option.isEmpty, msg)
  }

  @Test def testPath(): Unit = {
    println("path")
    val tree = new IntegerTree(CollatzFunctions.classic)
    val initial = 5
    var curr = initial
    var expected = List(curr)
    while (curr > 1) {
      curr = tree.successor(curr)
      expected = expected :+ curr
    }
    val option = tree.path(initial)
    if (option.isEmpty) {
      val msg = "Queried path from " + initial +
        " by Collatz function should not be empty"
      fail(msg)
    } else {
      val actual = option.get
      assertEquals(expected, actual)}
  }

  @Test def testNoPath(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.classic)
    assertEquals(0, tree.successor(0))
    val option = tree.path(0, 4)
    val msg = "There should be no Collatz path from 0 to 4"
    assert(option.isEmpty, msg)
  }

  @Test def testCycle(): Unit = {
    println("cycle")
    val tree = new IntegerTree(CollatzFunctions.negCollatz)
    val number = 13
    val pathLead = tree.successor(number)
    val path = tree.path(pathLead, number).get
    val option = tree.cycle(number)
    if (option.isEmpty) {
      val msg = "Queried cycle from and to 13 should not be empty"
      fail(msg)
    } else {
      val expected = number :: path
      val actual = option.get
      assertEquals(expected, actual)
    }
  }

  @Test def testCycleCyclicality(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.negCollatz)
    var cycle = tree.cycle(13).get.dropRight(1)
    val len = cycle.size
    var counter = 0
    while (counter < len) {
      val recur = cycle.head
      val expected = cycle :+ recur
      val actual = tree.cycle(recur).get
      assertEquals(expected, actual)
      cycle = cycle.drop(1) :+ recur
      counter += 1
    }
  }

  @Test def testCycleOfOne(): Unit = {
    def identity(n: Int): Int = n
    val tree = new IntegerTree(identity)
    val option = tree.cycle(7)
    if (option.isEmpty) {
      fail("Cycle containing only one number shouldn't be empty")
    } else {
      val expected = List(7)
      val actual = option.get
      assertEquals(expected, actual)
    }
  }

  @Test def testQuery(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.negCollatz)
    tree.scan(-89 to 53)
    val expected = List(53, -158, -79, 238, 119, -356, -178, -89)
    val actual = tree.query(53, -89)
    assertEquals(expected, actual)
  }

  @Test def testQueryShouldAnswerWithoutPriorScan(): Unit = {
    val tree = new IntegerTree(CollatzFunctions.classic)
    val expected = List(84, 42, 21, 64)
    val actual = tree.query(84, 64)
    assertEquals(expected, actual)
  }

  @Test def testQueryInterruptedIteration(): Unit = {
    val tree = new IntegerTree(IntegerTreeTest.collatzVariantQO)
    try {
      val badResult = tree.query(7)
      val msg = "Querying 7 on " + tree.toString +
        " should have caused an exception, not given result " +
        badResult.toString
      fail(msg)
    } catch {
      case ae: ArithmeticException => val msg = "Querying 7 on " + tree.toString +
        " caused ArithmeticException that should have been wrapped"
        println("\"" + ae.getMessage + "\"")
        fail(msg)
      case iie: InterruptedIterationException =>
        val expected = List(7, 889, 112903, 14338681, 1821012487)
        val actual = iie.partial
        assertEquals(expected, actual)
    }
  }

  @Test def testScan(): Unit = {
    println("scan")
    val tree = new IntegerTree(CollatzFunctions.negCollatz)
    tree.scan(-194 to 52)
    val option = tree.path(52, -194)
    if (option.isEmpty) {
      val msg = "After scan, path from 52 to -194 by -3x + 1 should have been found"
      fail(msg)
    } else {
      val expected = List(52, 26, 13, -38, -19, 58, 29, -86, -43, 130, 65, -194)
      val actual = option.get
      assertEquals(expected, actual)
    }
  }

}

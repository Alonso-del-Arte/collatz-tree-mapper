package main

import scala.util.Random

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._

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
      val msg = "Queried path from " + initial + " should not be empty"
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

  @Test def testScan(): Unit = {
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

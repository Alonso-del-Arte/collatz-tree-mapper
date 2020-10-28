package main

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._

class IntegerTreeTest {

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

  // TODO: Write test
  @Test def testPrecursorsPathPertainingTo1(): Unit = {
    fail("Haven't written test yet")
  }

}

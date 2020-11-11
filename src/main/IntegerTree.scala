package main

import scala.collection.immutable.HashMap

object IntegerTree {

  val GENERAL_ITERATION_MAXIMUM = 64

}

/**
 * Represents a partially calculated tree of integers arranged according to a
 * function like the Collatz function 3<i>n</i> + 1.
 * @param fn A function, preferably a pure function. If the range of the
 *           function exceeds the range of <code>Int</code>,
 */
class IntegerTree(val fn: Int => Int) {
  private var map: HashMap[Int, IntegerNode] = HashMap(1 -> new IntegerNode(1))
  private var counter = 0
  private var curr = 1
  while (!this.map(curr).hasNextAttached
    && counter < IntegerTree.GENERAL_ITERATION_MAXIMUM) {
    curr = this.map(curr).getNext.number
    counter += 1
  }

  private def retrieveNode(n: Int): IntegerNode = {
    if (this.map.contains(n)) {
      this.map(n)
    } else {
      val node = new IntegerNode(n)
      this.map = this.map + (n -> node)
      node
    }
  }

  /**
   * Retrieves whatever precursors of a particular number have been encountered
   * so far. This is not a pure function.
   * @param n The number for which to find precursors of. For example, 16 for
   *          the Collatz function.
   * @return The set of precursors of <code>n</code> that this tree has
   *         encountered so far. For example, a set containing 5 and 32 if both
   *         of those numbers have been encountered by this tree. If neither
   *         number has been encountered, this function will return an empty
   *         set.
   */
  def precursors(n: Int): Set[Int] = {
    val precursorNodes = this.retrieveNode(n).getPrevious
    precursorNodes.map(_.number)
  }

  /**
   * Gives the successor of a particular number. This is not a pure function
   * because it will sometimes have the side effect of calculating more of the
   * tree.
   * @param n The number to give the successor of. For example, 16.
   * @return The successor of <code>n</code>. As long as no exception occurred
   *         in the computation, this function will always give a result. For
   *         example, 8.
   */
  def successor(n: Int): Int = {
    retrieveNode(n).getNext.number
  }

  /**
   * Builds more of the tree.
   * @param range An arithmetic progression of integers.
   */
  def scan(range: Range): Unit = {
    for (n <- range) retrieveNode(n)
  }

  def path(from: Int, to: Int = 1): Option[List[Int]] = {
    if (this.map.contains(from) && this.map.contains(to)) {
      var curr = from
      var currNode = this.retrieveNode(curr)
      var addFlag = true
      var list = List(currNode)
      do {
        currNode = currNode.getNext
        curr = currNode.number
        addFlag = !list.contains(currNode)
        if (addFlag) list = list :+ currNode
      } while (addFlag && curr != to)
      if (addFlag) Option(list.map(_.number)) else Option.empty
    } else {
      Option.empty
    }
  }

  private class IntegerNode(val number: Int) {
    private var previous: Set[IntegerNode] = Set()
    private var next: IntegerNode = _
    var hasNextAttached: Boolean = false

    def attachPrevious(node: IntegerNode): Unit = {
      this.previous = this.previous + node
    }

    def getPrevious: Set[IntegerNode] = this.previous

    def attachNext(node: IntegerNode): Unit = {
      if (this.hasNextAttached) {
        val msg = "This node already has a next node attached"
        throw new IllegalStateException(msg)
      }
      this.next = node
      this.hasNextAttached = true
      this.next.attachPrevious(this)
    }

    def getNext: IntegerNode = {
      if (this.hasNextAttached) this.next else {
        val successor = IntegerTree.this.fn(this.number)
        val successorNode = if (IntegerTree.this.map.contains(successor)) {
          IntegerTree.this.map(successor)
        } else {
          new IntegerNode(successor)
        }
        successorNode.attachPrevious(this)
        this.attachNext(successorNode)
        IntegerTree.this.map = IntegerTree.this.map +
          (successor -> successorNode)
        successorNode
      }
    }

  }

}

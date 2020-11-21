package main

import scala.collection.immutable.HashMap

object IntegerTree {

  val GENERAL_ITERATION_MAXIMUM = 64

}

/**
 * Represents a partially calculated tree of integers arranged according to a
 * function like the Collatz function 3<i>n</i> + 1. Upon initialization, this
 * tree tries to determine the cycle involving 1.
 * @param fn A function, preferably a pure function. If the range of the
 *           function exceeds the range of `Int`, the function may throw
 *           `ArithmeticException`. This is not required, but it is preferable
 *           to having the function give potentially incorrect results.
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
   * @return The set of precursors of `n` that this tree has encountered so
   *         far. For example, a set containing 5 and 32 if both of those
   *         numbers have been encountered by this tree. If neither number has
   *         been encountered, this function will return an empty set.
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
   * @return The successor of `n`. As long as no exception occurred in the
   *         computation, this function will always give a result. For example,
   *         8.
   */
  def successor(n: Int): Int = {
    this.retrieveNode(n).getNext.number
  }

  /**
   * Builds more of the tree. Then more numbers will be considered previously
   * encountered by this tree.
   * @param range An arithmetic progression of integers. For example, &minus;90
   *              to 90 by 3.
   */
  def scan(range: Range): Unit = {
    for (n <- range) this.retrieveNode(n)
  }

  /**
   * Determines a path from one integer to another according to this tree's
   * function. The path involves at least two distinct integers. This is not a
   * pure function. The starting integer and the ending integer should already
   * have been encountered, otherwise this will return an empty option. But if
   * the intervening integers have not been encountered, they will be added to
   * the tree.
   * @param from The integer to start from. For example, 85.
   * @param to The integer to end up at. If omitted, 1 will be filled in.
   * @return An empty option if no path with at least two distinct integers
   *         could be found, or an option with a list that starts with the
   *         starting integer and ends with the ending integer. For example,
   *         given the Collatz function 3<i>n</i> + 1 for <i>n</i> odd and
   *         <sup><i>n</i></sup>&frasl;<sub>2</sub> for <i>n</i> even, starting
   *         with 85 and ending with 1, the list will consist of 85, 256, 128,
   *         64, 32, 16, 8, 4, 2, 1, assuming 85 has been previously
   *         encountered (1 should have been encountered upon initiialization).
   */
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

  /**
   * Determines a cycle on a given number according to this tree's function.
   * @param number The number the cycle is on. For example, 4.
   * @return An empty option if no cycle could be found, otherwise an option
   *         with a list of integers. If present, the list will begin and end
   *         with the specified number. For example, with the Collatz function
   *         and the number 4, the list will consist of 4, 2, 1, 4. If a number
   *         is its own successor, the list will contain only that number
   *         exactly once (for example, 0 with almost every variant of the
   *         Collatz function).
   */
  def cycle(number: Int): Option[List[Int]] = {
    val list = List(number)
    val first = this.retrieveNode(number)
    val second = first.getNext
    if (first == second) {
      Option(list)
    } else {
      val option = this.path(second.number, number)
      if (option.isEmpty) {
        option
      } else {
        Option(list ++ option.get)
      }
    }
  }

  // STUB TO FAIL THE FIRST TEST
  def query(start: Int, end: Int = 1): List[Int] = List(0)

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
        val successorNode = IntegerTree.this.retrieveNode(successor)
        successorNode.attachPrevious(this)
        this.attachNext(successorNode)
        successorNode
      }
    }

  }

}

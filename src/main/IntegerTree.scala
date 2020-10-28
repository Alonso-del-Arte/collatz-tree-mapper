package main

import scala.collection.immutable.HashMap

object IntegerTree {

  val GENERAL_ITERATION_MAXIMUM = 64

}

class IntegerTree(val fn: Int => Int) {
  private var map: HashMap[Int, IntegerNode] = HashMap()

  private def retrieveNode(n: Int): IntegerNode = {
    if (this.map.contains(n)) {
      this.map(n)
    } else {
      val node = new IntegerNode(n)
      this.map = this.map + (n -> node)
      node
    }
  }

  def precursors(n: Int): Set[Int] = {
    val precursorNodes = this.retrieveNode(n).getPrevious
    precursorNodes.map(_.number)
  }

  def successor(n: Int): Int = {
    retrieveNode(n).getNext.number
  }

  // STUB TO FAIL THE FIRST TEST
  def path(from: Int, to: Int = 1): Option[List[Int]] = Option.empty

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
          val node = new IntegerNode(successor)
          node.attachPrevious(this)
          node
        }
        successorNode.attachPrevious(this)
        this.attachNext(successorNode)
        IntegerTree.this.map = IntegerTree.this.map + (successor -> successorNode)
        successorNode
      }
    }

  }

}

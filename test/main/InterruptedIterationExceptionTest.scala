package main

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._

class InterruptedIterationExceptionTest {

  // TODO: Figure out more idiomatic way to do this test
  @Test def testConstructorRejectsEmptyList(): Unit = {
    val excMsg = "For testing purposes"
    val arithmeticException = new ArithmeticException("Integer overflow")
    try {
      val badException = new InterruptedIterationException(excMsg,
        arithmeticException, List(), CollatzFunctions.oneXPlus1)
      val msg = "Should not have been able to construct exception " +
        badException.toString + " with empty list"
      fail(msg)
    } catch {
      case iae: IllegalArgumentException =>
        println("Trying to use empty list correctly caused IllegalArgumentException")
        println("\"" + iae.getMessage + "\"")
      case re: RuntimeException => val msg = re.getClass.getName +
        " is the wrong exception to throw for empty list parameter"
        fail(msg)
    }
  }

}

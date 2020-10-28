package fileops

import java.io.{File, FileWriter}
import java.time.LocalDateTime

import org.junit.jupiter.api.{AfterAll, BeforeAll, Test, TestInstance}
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
class FileChooserWithOverwriteGuardTest {
  private val tempDirPath = System.getProperty("java.io.tmpdir")
  private val tempDir = new File(tempDirPath)
  private val fileCreationDate = LocalDateTime.now
  private val exampleFileName = "example_" +
    fileCreationDate.toString.replace(":", "") + ".txt"
  private val examplePath = tempDirPath + File.separatorChar + exampleFileName
  private val exampleFile = new File(examplePath)

  @BeforeAll def setUpClass(): Unit = {
    val msg1 = "File " + exampleFileName + " should not already exist"
    assert(!exampleFile.exists(), msg1)
    if (exampleFile.createNewFile()) {
      println("Successfully created " + exampleFileName + " in " + tempDirPath)
    }
    val fileContents = "This file created on " + fileCreationDate.toString +
      " should have been deleted when the tests concluded. Please close and delete."
    val writer = new FileWriter(exampleFile)
    writer.write(fileContents)
    writer.close()
    val msg2 = "Example file should have nonzero file size"
    assert(exampleFile.getTotalSpace > 0, msg2)
  }

  // TODO: Write test
  @Test def testApproveSelection(): Unit = {
    val fileChooser = new FileChooserWithOverwriteGuard
    fileChooser.setSelectedFile(exampleFile)
    fail("Haven't written test yet")
  }

  @AfterAll def tearDownClass(): Unit = {
    if (exampleFile.delete()) {
      println("Successfully deleted " + exampleFileName)
    } else {
      println("Unable to delete " + exampleFile.getAbsolutePath)
    }
  }

}

package fileops

import java.io.{File, FileWriter, IOException}
import java.time.LocalDateTime

import org.junit.jupiter.api.{AfterAll, AfterEach, BeforeAll, BeforeEach, Test, TestInstance}
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
class FileChooserWithOverwriteGuardTest {
  private val tempDirPath = System.getProperty("java.io.tmpdir")
  private val tempDir = new File(tempDirPath)
  private val exampleFileName = "example_" + LocalDateTime.now().toString + ".txt"
  private val examplePath = tempDirPath + File.separatorChar + exampleFileName
  private val exampleFile = new File(examplePath)

  @BeforeAll def setUpClass(): Unit = {
    if (exampleFile.exists()) {
      println("Example file " + exampleFileName + "already exists in " +
        tempDirPath + "; it will be overwritten and deleted")
    } else {
      println("Successfully created " + exampleFileName + " in " + tempDirPath)
    }
    // TODO: Overwrite example file
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

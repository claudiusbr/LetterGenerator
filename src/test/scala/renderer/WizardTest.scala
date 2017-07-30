package renderer

import scala.swing.FileChooser
import java.io.File

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar

class WizardTest extends FunSpec 
  with GivenWhenThen with MockitoSugar{
  
  val wiz = new Wizard
  
  describe("The getFile method") {
    it("should get an absolute file name") {
      Given("a FileChooser")
      val opener = mock[FileChooser] 
      val file = mock[File]
      when(opener.showOpenDialog(null)).thenReturn(FileChooser.Result.Approve)
      when(opener.selectedFile).thenReturn(file)
      when(file.getAbsolutePath).thenReturn("testPath")

      When("the method is called")
      val path = wiz.getFile(opener)

      Then("the chosen file's absolute path should be returned")
      assert(path == "testPath")
    }
  }

}

package renderer

import scala.swing.MainFrame

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar

class IntMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar{
  
  //failing test
  describe("the runInterface method") {
    it("should run the GUI") {
      assert(false)
    }
  }

}


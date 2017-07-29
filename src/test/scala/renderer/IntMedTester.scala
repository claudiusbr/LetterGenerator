package renderer

import scala.swing.MainFrame

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar

class IntMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar{
  
  val mockFrame: MainFrame = mock[MainFrame]
  
  val im = InteractionMediator(mockFrame)

  describe("the runInterface method") {
    it("should run the GUI") {
      When("it is called")
      im.runInterface()
      
      Then("the frame should become visible")
      Mockito.verify(mockFrame, Mockito.times(1)).visible_=(true)
    }
  }

}


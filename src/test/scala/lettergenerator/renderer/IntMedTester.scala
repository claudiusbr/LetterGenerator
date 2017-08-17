package lettergenerator
package renderer


import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar

class IntMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar{
  
  val mockFrame: Wizard = mock[Wizard]
  
  val im = InteractionMediator()

  describe("the registerInterface method") {
    it("should add a MainFrame ") {
      Given("a recently initialized class without a GUI")
      assert(im.hasGui == false)

      When("registerInterface is called with the right parameters")
      im.registerInterface(mockFrame)

      Then("it should save a reference to the given GUI")
      assert(im.hasGui == true)
    }
  }

  describe("the runInterface method") {
    it("should run the GUI") {
      When("the method is called")
      im.runInterface()
      
      Then("the frame should become visible")
      Mockito.verify(mockFrame, Mockito.times(1)).visible_=(true)
    }
  }
  
  describe("the messageUser method") {
    it("should call the message method from the gui") {
      Given("a message as a string of text")
      val msg = ""
      
      When("the message is passed to the method")
      im.messageUser(msg)
      
      Then("the gui's message method should be called")
      Mockito.verify(mockFrame, Mockito.times(1)).message(msg)
    }
  }

}


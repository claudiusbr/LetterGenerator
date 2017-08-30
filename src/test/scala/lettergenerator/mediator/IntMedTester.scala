package lettergenerator
package mediator

import renderer.Wizard

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar

class IntMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar {
  
  val mockGui: Wizard = mock[Wizard]
  
  val im = new InteractionMediator()

  describe("the registerInterface method") {
    it("should add a MainFrame ") {
      Given("a recently initialized class without a GUI")
      assert(im.hasGui == false)

      When("registerInterface is called with the right parameters")
      im.registerInterface(mockGui)

      Then("it should save a reference to the given GUI")
      assert(im.hasGui == true)
    }
  }

  describe("the runInterface method") {
    it("should run the GUI") {
      When("the method is called")
      im.runInterface()
      
      Then("the frame should become visible")
      Mockito.verify(mockGui, Mockito.times(1)).visible_=(true)
    }
  }
}


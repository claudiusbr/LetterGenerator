package lettergenerator
package validators

import org.scalatest.mockito.MockitoSugar
import org.scalatest.GivenWhenThen
import org.scalatest.FunSpec

class ErrorMsgFactoryTester extends FunSpec with
  GivenWhenThen with MockitoSugar {
  
  val errorMsg = ErrorMessageFactory
  
  describe("the apply method") {
    
    it("should return a path error message"){
      Given("a path validator")
      val path = mock[PathValidator]
      
      When("it is passed to the factory")
      val msg = errorMsg(path)

      Then("it returns a path error message")
      assert(msg == "Could not reach the %s. Please check if path " +
        "is correct, or report this issue")
    }
    
    it("should return a details error message"){
      Given("an instance of details validator")
      val details = mock[DetailsValidator]

      When("it is passed to the factory")
      val msg = errorMsg(details)
      
      Then("it returns a details error message")
      assert(msg == "Details file error: the row with values %s is " + 
        "incomplete. Please check it and try again")
    }

    it("should return a template error message"){
      Given("a template validator")
      val template = mock[TemplateValidator]
      
      When("it is passed to the factory")
      val msg = errorMsg(template)
      
      Then("it returns a template error message")
      assert(msg == "Error: could not find variable %s on template.")
    }
  }
}

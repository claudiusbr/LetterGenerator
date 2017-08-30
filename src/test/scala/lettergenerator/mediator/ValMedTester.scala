package lettergenerator
package mediator

import renderer.Wizard
import validators._

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.{Mockito, Matchers}
import org.mockito.AdditionalMatchers.not
import org.scalatest.mockito.MockitoSugar

class ValMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar {
  
  val mockGui: Wizard = mock[Wizard]
  val mockPathValidator: PathValidator = mock[PathValidator]
  
  val vm = new ValidationMediator(mockGui)
  
  when(mockPathValidator.validate(
    Matchers.matches("./valid/path")))
    .thenReturn(true)

  when(mockPathValidator.validate(
    not(Matchers.eq("./valid/path"))))
    .thenReturn(false)

  val path = "./valid/path"
  val otherPath = "./some/path"
  
  describe("the validatePath method") {
    it("should return Some") {
      Given("a path that exists")

      Then("it returns Some(path)")
      assert(vm.validatePath(path, mockPathValidator).get == "./valid/path")
    }
    
    it("should return None") {
      Given("a path which does not exist")
      Then("it returns None")
      assert(vm.validatePath(otherPath, mockPathValidator) == None)
    }
  }
  
  describe("the validatePathOrThrow method") {
    it("should return the path String") {
      Given("a path which exists")
      Then("it should return return the path")
      assert(false)
    }
    
    it("should throw an exception") {
      Given("a path which does not exist")
      Then("it should message the user")
      assert(false)
      And("it should throw an exception")
      assert(false)
    }
  }
  
  describe("the validateAllPaths method") {
    it("should validate all paths entered by the user") {
      Given("existing paths for the details and template file, and the destination folder")
      Then("it should message the user that all paths are valid")
      assert(false)
    }
    
    it("should throw an exception") {
      Given("an invalid details file path")
      Then("it should message the user that details cannot be found")
      assert(false)
      And("it should throw an exception")
      assert(false)

      Given("an invalid template file path")
      Then("it should message the user that the template cannot be found")
      assert(false)
      And("it should throw an exception")
      assert(false)

      Given("an invalid destination folder path")
      Then("it should message the user that the destination cannot be found")
      assert(false)
      And("it should throw an exception")
      assert(false)
    }
  }
}





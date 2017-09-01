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
  
  val path = "./valid/path"
  val otherPath = "./some/path"
  
  when(mockPathValidator.validate(
    Matchers.matches(path)))
    .thenReturn(true)

  when(mockPathValidator.validate(
    not(Matchers.eq(path))))
    .thenReturn(false)
    
  describe("the validatePath method") {
    it("should return Some") {
      Given("a path that exists")
      Then("it returns Some(path)")
      assert(vm.validatePath(path, mockPathValidator).get == path)
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
      assert(vm.validatePathOrThrow(("valid",path),mockPathValidator) == path)
    }
    
    it("should throw an exception") {
      Given("a path which does not exist")

      Then("it should throw an exception")
      assertThrows[Exception](
        vm.validatePathOrThrow(("not valid",otherPath),mockPathValidator))

      And("it should message the user")
      Mockito.verify(mockGui,Mockito.times(1))
        .message(Matchers.anyString())
    }
  }
  
  describe("the validateAllPaths method") {
    val pathMessage = "Could not reach the %s. Please check if path is correct" +
      ", or report this issue"

    it("should validate all paths entered by the user") {
      Given("existing paths for the details and template file, and the destination folder")
      when(mockGui.detailsFile).thenReturn(path)
      when(mockGui.templateFile).thenReturn(path)
      when(mockGui.destinationFolder).thenReturn(path)

      Then("it should message the user")
      vm.validateAllPaths(mockPathValidator)
      Mockito.verify(mockGui,Mockito.times(1)).message("File paths validated")
    }
    
    it("should throw an exception if it can't reach the details file") {
      Given("an invalid details file path")
      when(mockGui.detailsFile).thenReturn(otherPath)
      when(mockGui.templateFile).thenReturn(path)
      when(mockGui.destinationFolder).thenReturn(path)

      Then("it should throw an exception")
      assertThrows[Exception](vm.validateAllPaths(mockPathValidator))
      And("it should message the user that the details file cannot be found")
      Mockito.verify(mockGui,Mockito.times(1)).message(pathMessage.format("details file"))
    }

    it("should throw an exception if it can't reach the template file") {
      Given("an invalid template file path")
      when(mockGui.detailsFile).thenReturn(path)
      when(mockGui.templateFile).thenReturn(otherPath)
      when(mockGui.destinationFolder).thenReturn(path)

      Then("it should throw an exception")
      assertThrows[Exception](vm.validateAllPaths(mockPathValidator))
      And("it should message the user that the template file cannot be found")
      Mockito.verify(mockGui,Mockito.times(1)).message(pathMessage.format("template file"))
    }

    it("should throw an exception if it can't reach the destination folder") {
      Given("an invalid destination folder path")
      when(mockGui.detailsFile).thenReturn(path)
      when(mockGui.templateFile).thenReturn(path)
      when(mockGui.destinationFolder).thenReturn(otherPath)


      Then("it should throw an exception")
      assertThrows[Exception](vm.validateAllPaths(mockPathValidator))
      And("it should message the user that the destination folder cannot be found")
      Mockito.verify(mockGui,Mockito.times(1)).message(pathMessage.format("destination folder"))
    }
  }
}





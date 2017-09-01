package lettergenerator
package mediator

import renderer.Wizard
import formatter._

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar

class LoadMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar {
  
  val mockGui: Wizard = mock[Wizard]
  val mockDetailsFormatter: DetailsFormatter = mock[DetailsFormatter]
  
  val lm = new LoadingMediator(mockGui)

  val validDetailsPath = "./valid/path"
  
  val detailsList: List[Map[String,String]] = List(
    Map("name" -> "The Quick Brown Fox", 
      "action" -> "Jumped Over The Lazy Dog",
      "consequence" -> "+35XP"),

    Map("name" -> "The Lazy Dog",
        "action" -> "Was Jumped Over By The Quick Brown Fox",
        "consequence" -> "Had To Re-evaluate His Life Choices"))

  when(mockGui.detailsFile).thenReturn(validDetailsPath)
  
  when(mockDetailsFormatter.details).thenReturn(detailsList)
  
  describe("the loadDetails method") {
    it("does what it says on the tin") {
      Given("a valid path to a csv file containing the details")
      When("the method is called")
      Then("it returns the data from the details file")
      assert(lm.loadDetails(mockDetailsFormatter) == detailsList)
    }
  }
  
  describe("the loadTemplate method") {
    it("loads a docx template") {
      Given("a valid WordML formatter with a docx template file path")
      When("the method is called")
      Then("it returns a string with the contents of the ")
      assert(false)
    }
  }

}
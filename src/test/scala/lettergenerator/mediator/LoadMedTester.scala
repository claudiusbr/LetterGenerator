package lettergenerator
package mediator

import renderer.Wizard
import formatter._

import org.docx4j.openpackaging.packages.WordprocessingMLPackage

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar

class LoadMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar {
  
  val mockGui: Wizard = mock[Wizard]
  val mockDetailsFormatter: DetailsFormatter = mock[DetailsFormatter]
  
  val lm = new LoadingMediator(mockGui)

  val validPath = "./valid/path"

  val detailsList: List[Map[String,String]] = List(
    Map("name" -> "The Quick Brown Fox", 
      "action" -> "Jumped Over The Lazy Dog",
      "consequence" -> "+35XP"),

    Map("name" -> "The Lazy Dog",
        "action" -> "Was Jumped Over By The Quick Brown Fox",
        "consequence" -> "Had To Re-evaluate His Life Choices"))

  
  when(mockDetailsFormatter.details).thenReturn(detailsList)
  
  describe("the loadDetails method") {
    it("does what it says on the tin") {
      Given("a valid path to a csv file containing the details")
      val validDetailsPath = validPath
      when(mockGui.detailsFile).thenReturn(validDetailsPath)

      When("the method is called")
      val detailsOutput = lm.loadDetails(mockDetailsFormatter)
      
      Then("it returns the data from the details file")
      assert(detailsOutput.tuples == detailsList)
    }
  }
  
  describe("the loadTemplate method") {
    it("loads a docx template") {
      Given("a valid template formatter with a docx template file path")
      val mockTemplateFormatter = mock[TemplateFormatter]
      val mockWordML = mock[WordprocessingMLPackage]
      val validTemplatePath = validPath
      when(mockGui.templateFile).thenReturn(validTemplatePath)
      when(mockTemplateFormatter.template).thenReturn(mockWordML)

      When("the method is called")
      val templateOutput = lm.loadTemplate(mockTemplateFormatter)

      Then("it returns a WodprocessingMLPackage instance of the docx")
      assert(templateOutput == mockWordML)
    }
  }
}
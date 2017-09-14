package lettergenerator
package mediator

import renderer.Wizard
import formatter._

import org.docx4j.openpackaging.packages.WordprocessingMLPackage

import org.mockito.Mockito.when

class LoadMedTester extends Tester {

  val testObjects = new TestObjects with DetailsTestObjects
  
  val mockGui: Wizard = testObjects.mockGui
  val mockDetailsFormatter: DetailsFormatter = mock[DetailsFormatter]
  
  val lm = new LoadingMediator(mockGui)

  val validPath = "./valid/path"

  val detailsList: List[Map[String,String]] = testObjects.tuples
  
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
      val mockTemplate = mock[Template]
      val validTemplatePath = validPath
      when(mockGui.templateFile).thenReturn(validTemplatePath)
      when(mockTemplateFormatter.template).thenReturn(mockTemplate)

      When("the method is called")
      val templateOutput = lm.loadTemplate(mockTemplateFormatter)

      Then("it returns a WodprocessingMLPackage instance of the docx")
      assert(templateOutput == mockTemplate)
    }
  }
}

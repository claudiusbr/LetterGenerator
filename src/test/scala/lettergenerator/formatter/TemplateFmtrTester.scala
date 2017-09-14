package lettergenerator
package formatter

import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.docx4j.openpackaging.packages.WordprocessingMLPackage

class TemplateFmtrTester extends Tester {
  val testObjects = new TestObjects with TemplateTestObjects
  
  val mockInput = testObjects.mockInput
  val mockDocPack = testObjects.mockDocPack
  
  
  describe("the template field") {
    it("should contain and return an instance of Template"){
      Given("an appropriate Input")
      doReturn(mockDocPack).when(mockInput).getContents()

      When("the TemplateFormatter is instantiated")
      val templateFormatter = new TemplateFormatter(mockInput)
      
      Then("it should retrieve and type cast the contents of the Input")
      Mockito.verify(mockInput,Mockito.times(1)).getContents()
      
      And("it should contain and return an instance of Template with the Input's contents")
      assert(templateFormatter.template.docPack == mockDocPack)
    }
  }
  
}
package lettergenerator
package formatter

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.{Mockito, Matchers}
import org.scalatest.mockito.MockitoSugar

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

class TemplateTester extends FunSpec 
  with GivenWhenThen with MockitoSugar {
  
  val mockJaxbElement = mock[Document]
  val mockNewJaxbElement = mock[Document]
  val mockMainPart = mock[MainDocumentPart]
  val mockDocPack = mock[WordprocessingMLPackage]

  when(mockDocPack.getMainDocumentPart).thenReturn(mockMainPart)
  when(mockMainPart.getJaxbElement).thenReturn(mockJaxbElement)
  
  val template = new Template(mockDocPack)
  
  describe("the docPack field") {
    it("returns the WordprocessingMLPackage passed as an argument to the constructor") {
      Given("an instance of the Template class")
      When("the accessor to docPack is called")
      val testDocPack = template.docPack
      
      Then("it returns the instance of WordprocessingMLPackage used to instantiate it")
      assert(testDocPack == mockDocPack)
    }
  }

  describe("the mainDocumentPart method") {
    it("returns the template's MainDocumentPart") {
      Given("an instance of the Template class")
      When("the method is called")
      val testMainPart = template.mainDocumentPart
      
      Then("it returns a MainDocumentPart instance corresponding to the template")
      assert(testMainPart == mockMainPart)
    }
  }
  
  describe("the jaxbElement method") {
    it("returns the content of the template") {
      Given("an instance of the Template class")
      
      When("the method is called with no arguments")
      val testDoc = template.jaxbElement
      
      Then("it returns the Document instance with the template's contents")
      assert(testDoc == mockJaxbElement)
    }
    
    it("registers new contents to the WordprocessingMLPackage instance") {
      Given("a new instance of Document")
      val newDoc = mockNewJaxbElement
      
      When("the method is called with a new instance of Document")
      template.jaxbElement = newDoc
      
      Then("the new instance should be registered in the WordprocessingMLPackage")
      Mockito.verify(mockMainPart,Mockito.times(1)).setJaxbElement(newDoc)
      
    }
  }
  
}
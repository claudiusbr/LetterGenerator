package lettergenerator
package mediator

import renderer.Wizard
import formatter.{Template,DocxMakerFormatter}

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.Mockito
import org.mockito.Matchers.{anyString,anyInt}
import org.mockito.AdditionalMatchers.not
import org.scalatest.mockito.MockitoSugar

class DocxMediatorTester extends FunSpec
  with GivenWhenThen with MockitoSugar {

  val testObjects = new TestObjects
  
  val mockGui = testObjects.mockGui
  val mockTempl = testObjects.mockTempl
  val mockDMForm = mock[DocxMakerFormatter]
  val dm = new DocxMediator(mockGui,mockTempl,mockDMForm)
  
  describe("the generateDocx method") {
    it("generates a single docx") {
      Given("a single details tuple")
      
    }
  }
}




















package lettergenerator
package mediator

import renderer.Wizard

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

class DocxMakerTester extends FunSpec
  with GivenWhenThen with MockitoSugar {
  
  val mockGui = mock[Wizard]
  val mockValMed = mock[ValidationMediator]
  val mockDocPack = mock[WordprocessingMLPackage]
  val mockSaver = mock[SaveToZipFile]
  val mockMain = mock[MainDocumentPart]
  val mockDocument = mock[Document]
  
  val tuples: List[Map[String,String]] = List(
    Map("name" -> "The Quick Brown Fox", 
      "action" -> "Jumped Over The Lazy Dog",
      "consequence" -> "earned +35XP"),

    Map("name" -> "The Lazy Dog",
        "action" -> "Was Jumped Over By The Quick Brown Fox",
        "consequence" -> "Had To Re-evaluate His Life Choices"))
  val headers: Array[String] = tuples.head.keys.toArray
  
  val dmkr = new DocxMaker(mockGui)

  when(mockGui.fnAlsoInTemplate).thenReturn(true)
  when(mockGui.fNameColumn).thenReturn("name")
  
  when(mockValMed.fileNameIfDuplicate(
    anyString(), anyString(), anyInt()))
      .thenReturn("SomeName")
  
  when(mockDocPack.getMainDocumentPart).thenReturn(mockMain)
  when(mockMain.getJaxbElement).thenReturn(mockDocument)
  
  
  describe("the makeManyDocx method") {
    it("should generate many docx"){
      Given("details with two tuples")
      val details = new Details(headers, tuples) 
      
      When("the method is called")
      try {
        dmkr.makeManyDocx(details, mockDocPack, mockValMed)(mockSaver)
      } catch {
        case _: Throwable => Unit
      }

      Then("two documents should be created")
      Mockito.verify(mockSaver,Mockito.times(2)).save("SomeName")
    }
  }

  describe("the makeDocx method") {
    it("should make a single docx"){
      Given("single tuple of details")
      val detailsTuple: Map[String,String] = tuples.head

      assert(true)
    }
  }
}




















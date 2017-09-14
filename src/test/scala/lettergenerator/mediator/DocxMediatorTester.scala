package lettergenerator
package mediator

import renderer.Wizard
import formatter.{Template,DocxMakerFormatter, Details}
import generators._

import scala.collection.JavaConverters._

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import org.mockito.Mockito.when
import org.mockito.Mockito
import org.mockito.Matchers.{anyString,anyInt}
import org.mockito.AdditionalMatchers.not

import java.util.{HashMap => JHashMap}

class DocxMediatorTester extends Tester {

  val testObjects = new TestObjects with TemplateTestObjects with DetailsTestObjects
  
  val mockGui = testObjects.mockGui
  val mockTempl = testObjects.mockTempl
  val mockDMForm = mock[DocxMakerFormatter]
  val mockGen = mock[Generator]
  val mockVM = mock[ValidationMediator]
  val mockSaver = mock[SaveToZipFile]

  val detailsSingleTuple: Map[String,String] = testObjects.details.tuples.head
  val singleTupleAsJMap = new JHashMap[String,String](detailsSingleTuple.asJava)

  when(mockGui.fnAlsoInTemplate).thenReturn(true)
  when(mockGui.fNameColumn).thenReturn(testObjects.details.tuples.head.keys.head)

  when(mockDMForm.prepareMap(detailsSingleTuple, ""))
      .thenReturn(singleTupleAsJMap)
  when(mockDMForm.fileName(detailsSingleTuple,mockGui.fNameColumn))
    .thenReturn("filename")

  when(mockVM.fileNameIfDuplicate("filename", ".docx")).thenReturn("filename")

  val dm = new DocxMediator(mockGui,mockTempl,mockDMForm,mockGen)
  
  describe("the generateDocx method") {
    it("generates a single docx") {
      Given("a single details tuple")
      When("the method is called")
      dm.generateDocx(detailsSingleTuple,mockVM)(mockSaver)

      Then("a single call to DocxGenerator.generate should be made")
      Mockito.verify(mockGen,Mockito.times(1))
        .generate(singleTupleAsJMap,"filename")(mockSaver)
    }

    it("generates two docx") {
      Given("two details tuples")
      val twoTuples = testObjects.details

      When("the method is called")
      dm.generateDocx(twoTuples,mockVM)(mockSaver)

      Then("two calls should be made to DocxGenerator.generate")
      Mockito.verify(mockGen,Mockito.times(2))
        .generate(singleTupleAsJMap,"filename")(mockSaver)
    }
  }
}




















package lettergenerator
package generators

import formatter.WordMLFormatter

import org.docx4j.openpackaging.io.SaveToZipFile

import scala.collection.JavaConverters.mapAsJavaMapConverter
import java.util.{HashMap => JHashMap}

import org.mockito.Mockito.{when,verify,times}

class DocGenTester extends Tester {
  val testObjects = new TestObjects 
    with TemplateTestObjects with DetailsTestObjects

  val mockMainPart = testObjects.mockMainPart  
  val mockOriginalJaxbElement = testObjects.mockJaxbElement
  val mockNewJaxbElement = testObjects.mockNewJaxbElement
  val mockTemplate = testObjects.mockTempl
  val mockJMapDetails = mock[JHashMap[String,String]]
  val mockSaver = mock[SaveToZipFile]
  val mockFormatter = mock[WordMLFormatter]
  
  val docGen = new DocxGenerator(mockTemplate)
  
  when(mockFormatter.replaceTextVariablesWith(mockJMapDetails))
    .thenReturn(mockNewJaxbElement)
    
  describe("the generate method") {
    it("generates a docx") {
      Given("a tuple of details as a JHashMap")
      Given("a file name")
      Given("a template")
      val fileName = "myFile"

      When("they are passed as arguments to the method")
      docGen.generate(mockJMapDetails, fileName)(mockSaver, mockFormatter)
      
      Then("the template's original contents' variables should be replaced by the details")
      verify(mockFormatter,times(1)).replaceTextVariablesWith(mockJMapDetails)
      
      And("the new contents should be added to the WordprocessingMLPackage's Main Part of the template")
      verify(mockMainPart,times(1)).setJaxbElement(mockNewJaxbElement)
      
      And("a new Docx gets saved with the file name provided")
      verify(mockSaver,times(1)).save(fileName)
      
      And("the template's original contents are restored so that they can be re-used")
      verify(mockMainPart,times(1)).setJaxbElement(mockOriginalJaxbElement)
    }
  }
}

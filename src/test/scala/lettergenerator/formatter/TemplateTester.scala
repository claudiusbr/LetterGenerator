package lettergenerator
package formatter

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.{Mockito, Matchers}
import org.scalatest.mockito.MockitoSugar

import org.docx4j.openpackaging.packages.WordprocessingMLPackage

class TemplateTester extends FunSpec 
  with GivenWhenThen with MockitoSugar {
  
  val mockDocPack = mock[WordprocessingMLPackage]

  val temp = new Template(mockDocPack)
  
  describe("the docPack field") {
    it("returns the WordprocessingMLPackage passed as an argument to the constructor") {
      Given("an instance of the Template class")
      When("the accessor to docPack is called")
      val testDocPack = temp.docPack
      
      Then("it returns the instance of WordprocessingMLPackage used to instantiate it")
      assert(testDocPack == mockDocPack)
    }
  }
}
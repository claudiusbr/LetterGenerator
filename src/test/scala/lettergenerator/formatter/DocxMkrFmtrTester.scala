package lettergenerator
package formatter

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.{Mockito, Matchers}
import org.mockito.AdditionalMatchers.not
import org.scalatest.mockito.MockitoSugar

class DocxMkrFmtrTester extends FunSpec
  with MockitoSugar with GivenWhenThen {
  
  val dmForm = new DocxMakerFormatter
  
  describe("the prepareMap method") {
    it("returns a JHashMap with the file name column included") {
      Given("the value true for fileNameAlsoInTemplate")
      val fnInTemplate: Boolean = true
      
      When("")
    }
    it("returns a JHashMap without the file name column") {}
  }

  describe("the fileName method") {
    it("returns the file name chosen") {}
    it("returns Output") {}
  }
}
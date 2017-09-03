package lettergenerator
package mediator


import renderer.Wizard

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.{Mockito, Matchers}
import org.mockito.AdditionalMatchers.not
import org.scalatest.mockito.MockitoSugar

class DocxMakerTester extends FunSpec
  with GivenWhenThen with MockitoSugar {
  describe("the makeManyDocx method") {
    it("should generate many docx"){
      assert(false)
    }
  }
  describe("the makeDocx method") {
    it("should make a single docx"){
      assert(false)
    }
  }
}
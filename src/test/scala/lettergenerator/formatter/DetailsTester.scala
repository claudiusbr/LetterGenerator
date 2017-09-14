package lettergenerator
package formatter

import org.scalatest.{FunSpec, GivenWhenThen}

class DetailsTester extends FunSpec with GivenWhenThen {
  
  val testObjects = new TestObjects with DetailsTestObjects
  
  describe("the auxiliary constructor") {
    it("should pass the keys of the head of the tuples list as headers if none is provided") {
      Given("a list of tuples")
      val tuples = testObjects.tuples
      
      When("they are passed to the constructor of the Details class")
      val headers: Array[String] = tuples.head.keys.toArray
      val details = new Details(tuples)
      
      Then("a new Details class should be created")
      assert(details.isInstanceOf[Details])
      
      And("it should have a field called \"headers\" equal to the keys of the head of the tuples list")
      assert(details.headers.deep == headers.deep)
    }
  }
}
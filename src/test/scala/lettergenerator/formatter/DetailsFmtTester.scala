package lettergenerator
package formatter

import org.mockito.Mockito.doReturn

class DetailsFmtrTester extends Tester {
  
  val testObjects = new TestObjects with DetailsTestObjects
  
  val mockInput = testObjects.mockInput

  val detailsFileContents: Iterator[String] = Seq[String](
    "name,action,consequence",
    "The Quick Brown Fox,Jumped Over The Lazy Dog,earned +35XP",
    "The Lazy Dog,Was Jumped Over By The Quick Brown Fox,Had To Re-evaluate His Life Choices")
      .toIterator

  val detailsFileContentsWithComma: Iterator[String] = Seq[String](
    "name,action,consequence",
    "The Quick Brown Fox,\"Jumped Over The Lazy Dog, on a Sunday\",earned +35XP",
    "The Lazy Dog,\"Was Jumped Over By The Quick Brown Fox, on a Sunday\","
      +"Had To Re-evaluate His Life Choices")
      .toIterator
      

  describe("the headers field") {
    it("should return the column names of the details file") {
      Given("a valid Input file")
      doReturn(detailsFileContents).when(mockInput).getContents()
      
      When("the formatter is instantiated")
      val detailsFmtr = new DetailsFormatter(mockInput)
      
      Then("the headers field should return all the column names as an array")
      assert(testObjects.headers.deep == detailsFmtr.headers.deep)

    }
  }

  describe("the details field") {
    it("should return a collection with the details file"){
      
    }
  }
}
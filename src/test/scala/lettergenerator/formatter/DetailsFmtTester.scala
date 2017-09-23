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
      

  describe("the constructor") {
    it("should build an instance with the correct fields") {
      Given("a valid Input file")
      doReturn(detailsFileContents).when(mockInput).getContents()
      
      When("the formatter is instantiated")
      val detailsFmtr = new DetailsFormatter(mockInput)
      
      Then("the headers field should return all the column names as an array")
      assert(testObjects.headers.deep == detailsFmtr.headers.deep)
      
      And("the details field should return a collection of the contents of the csv")
      assert(testObjects.tuples == detailsFmtr.details)

    }
    
    it("sould build a correct instance for details with comma") {
      Given("an input file from a CSV with commas in its 'cells'")
      doReturn(detailsFileContentsWithComma).when(mockInput).getContents()
      
      When("the formatter is instantiated")
      val detailsFmtr = new DetailsFormatter(mockInput)
      
      Then("the headers field should instantiate as normal")
      assert(testObjects.headers.deep == detailsFmtr.headers.deep)
      
      And("the details field should return a valid collection")
      val detailsForTest: List[Map[String,String]] = List(
        Map("name" -> "The Quick Brown Fox",
            "action" -> "Jumped Over The Lazy Dog, on a Sunday",
            "consequence" -> "earned +35XP" ),
            
        Map("name" -> "The Lazy Dog",
            "action" -> "Was Jumped Over By The Quick Brown Fox, on a Sunday",
            "consequence" -> "Had To Re-evaluate His Life Choices")
      )
      assert(detailsForTest == detailsFmtr.details)
    }
  }
}

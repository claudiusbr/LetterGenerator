package lettergenerator
package formatter

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.mockito.{Mockito, Matchers}
import org.scalatest.mockito.MockitoSugar

import java.util.{HashMap => JHashMap}

class DocxMkrFmtrTester extends FunSpec
  with MockitoSugar with GivenWhenThen {
  
  val testObjects = new TestObjects
  val dmForm = new DocxMakerFormatter
  
  describe("the prepareMap method") {
    it("returns a JHashMap with all the columns of the original map") {
      Given("a details tuple")
      val detailsTuple: Map[String,String] = testObjects.tuples.head
      
      When("the method is called with an empty string")
      val jmap: JHashMap[String,String] = dmForm.prepareMap(detailsTuple, "")
      
      Then("it returns a JHashMap with all the columns")
      import scala.collection.JavaConverters._
      assert(jmap.keySet().asScala == testObjects.headers.toSet)

    }

    it("returns a JHashMap without the file name column") {
      Given("a details tuple with a column to filter out")
      val detailsTuple: Map[String,String] = testObjects.tuples.head
      val columnToFilterOut: String = testObjects.headers.head
      
      When("the column to filter out is passed to the method as one of the arguments")
      val jmap: JHashMap[String,String] = dmForm.prepareMap(detailsTuple, columnToFilterOut)
      
      Then("it returns a JHashMap without the column")
      import scala.collection.JavaConverters._
      assert(jmap.keySet().asScala == testObjects.headers.filter(_ != columnToFilterOut).toSet)
    }
  }

  describe("the fileName method") {
    val detailsTuple: Map[String,String] = testObjects.tuples.head
    it("returns the file name chosen") {
      Given("the name of a column containing possible filenames")
      val fileNameColumn: String = testObjects.headers.head
      
      When("they are passed to the method as arguments")
      val fileName = dmForm.fileName(detailsTuple, fileNameColumn)
      
      Then("the method should return the value pertaining to the key which matches the column name")
      assert(fileName == detailsTuple(fileNameColumn))
    }

    it("returns Output") {
      Given("an empty column name")
      val fileNameColumn: String = ""
      
      When("it is passed to the method as an argument")
      val fileName = dmForm.fileName(detailsTuple, fileNameColumn)

      Then("the method should return the \"Output\" string")
      assert(fileName == "Output")
    }
  }
}
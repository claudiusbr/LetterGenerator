package lettergenerator
package mediator

import renderer.Wizard
import formatter._

import org.scalatest.{FunSpec, GivenWhenThen}
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar

class LoadMedTester extends FunSpec 
  with GivenWhenThen with MockitoSugar {
  
  val mockGui: Wizard = mock[Wizard]
  val mockDetailsFormatter: DetailsFormatter = mock[DetailsFormatter]
  
  val lm = new LoadingMediator(mockGui)

  val validDetailsPath = "./valid/path"
  
  val detailsList: List[Map[String,String]] = List(
    Map("name" -> "Inigo Montoya", 
      "action" -> "you killed my father",
      "consequence" -> "prepare to die"),
    Map("name" -> "Juan Pablo Montoya",
        "action" -> "you broke my car",
        "consequence" -> "prepare to pay"))

  when(mockGui.detailsFile).thenReturn(validDetailsPath)
  
  when(mockDetailsFormatter.details).thenReturn(detailsList)
  
  describe("the loadDetails method") {
    it("does what it says on the tin") {
      Given("a valid path to a csv file containing the details")
      Then("it returns the data from the details file")
      assert(lm.loadDetails(mockDetailsFormatter) == detailsList)
    }
  }

}
package lettergenerator
package mediator

import formatter._
import loader._
import renderer.Wizard

import scala.swing.MainFrame

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class InteractionMediator extends renderer.Interactor {
  private var gui: Wizard = _ 
  private var validator: ValidationMediator = _
  private var loader: LoadingMediator = _
  
  private val messageUser: String => Unit = (text: String) => gui.message(text)

  def registerInterface(gui: Wizard): Unit = {
    this.gui = gui
    validator = new ValidationMediator(this.gui)
    loader = new LoadingMediator(this.gui)         
  }

  def hasGui: Boolean = Option[MainFrame](gui) match {
    case Some(_) => true
    case None => false
  }
  
  def runInterface(): Unit = gui.visible = true
  
  def detailsFileHeaders(): List[String] = {

    val choiceForNoFileNameColumn = List(" ")

    if (!gui.detailsFile.isEmpty()) {
      choiceForNoFileNameColumn ++ new DetailsFormatter(CsvInput(validator
        .validatePathOrThrow(("details file",gui.detailsFile))))
          .details.head.keySet.toList
    } else {
      choiceForNoFileNameColumn 
    }

  }
  
  def submit(): Unit = {
    messageUser("Processing...")
    Future {
      validator.validateAllPaths()
      val details: Details = loader.loadDetails()
      validator.validateDetails(details)()
      val template: Template = loader.loadTemplate()
      validator.validateTemplate(details, template)()
      val generator = new DocxMediator(gui,template)
      generator.generateDocx(details, validator)()
      messageUser("Done!")
    }
  }
}

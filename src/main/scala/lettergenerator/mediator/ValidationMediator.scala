package lettergenerator.mediator

import lettergenerator._
import validators._

class ValidationMediator(gui: renderer.Wizard) extends UserMessenger(gui) {
  private val pathValidator = new PathValidator()
  
  val pathMessage = "Could not reach the %s. Please check if path is correct"+
    ", or report this issue"
    
  def detailsPathIfValid(): String = {
    val detailsFilePath: String = gui.detailsFile
    pathValidator.validate(detailsFilePath) match {
      case false => throw new Exception(pathMessage.format(detailsFilePath))
      case true => detailsFilePath
    }
  }

  def validateAllPaths(): Unit = {
    val paths = List[(String,String)](
      "details file" -> gui.detailsFile,
      "template file" -> gui.templateFile,
      "destination folder" -> gui.destinationFolder
    )

    pathValidator.applyRecursion[String]( paths,
      messageUser("File paths validated"), 
      (msg: String) => throw new Exception(pathMessage.format(msg)))
  } 
  
}
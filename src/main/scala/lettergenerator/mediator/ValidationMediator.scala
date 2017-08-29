package lettergenerator
package mediator

import validators._

class ValidationMediator(gui: renderer.Wizard) extends UserMessenger(gui) {
  private val pathValidator = new PathValidator()
  private val pathMessage = "Could not reach the %s. Please check if path is correct"+
    ", or report this issue"
    
  def validatePath(path: String, pathValidator: Validator = pathValidator): Option[String] = {
    pathValidator.validate(path) match {
      case true => Some(path)
      case false => None
    }
  }
  
  def validatePathOrThrow(path: String, pathValidator: Validator = pathValidator): String = {
    validatePath(path,pathValidator) match {
      case Some(e: String) => e
      case None => {
        val tellUser = pathMessage.format(path)
        messageUser(tellUser)
        throw new Exception(tellUser)
      }
    }
  }

  def validateAllPaths(pathValidator: RecursiveValidator = pathValidator): Unit = {
    val paths = List[(String,String)](
      "details file" -> gui.detailsFile,
      "template file" -> gui.templateFile,
      "destination folder" -> gui.destinationFolder
    )

    pathValidator.applyRecursion[String](
        paths, messageUser("File paths validated"), 
        (msg: String) => { 
          val tellUser = pathMessage.format(msg) 
          messageUser(tellUser) 
          throw new Exception(tellUser) 
        })
  } 
  
}
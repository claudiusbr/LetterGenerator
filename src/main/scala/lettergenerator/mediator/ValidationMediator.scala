package lettergenerator
package mediator

import validators._

class ValidationMediator(gui: renderer.Wizard) {
  private val errorMsgs = ErrorMessageFactory
  private val pathValidator = new PathValidator()
  def validatePath(path: String, pathValidator: Validator = pathValidator): Option[String] = {
    pathValidator.validate(path) match {
      case true => Some(path)
      case false => None
    }
  }
  
  def validatePathOrThrow(path: (String,String), 
    pathValidator: Validator = pathValidator): String = {
    validatePath(path._2,pathValidator) match {
      case Some(e: String) => e
      case None => {
        val tellUser = errorMsgs(pathValidator).format(path._1)
        gui.message(tellUser)
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

    paths.foreach(element => validatePathOrThrow(element, pathValidator))
    gui.message("File paths are valid.")
  } 
  
  def validateDetails(details: Details)(detailsValidator: RecursiveValidator = new 
    DetailsValidator(details.headers)): Unit = {
      try {
        for (map <- details.tuples) {
          detailsValidator.applyRecursion[Map[String,String]](
            List((map.values.mkString(" "),map)), 
            gui.message("Details are valid."),
            (msg: String) => 
              throw new Exception(errorMsgs(detailsValidator).format(msg)))
        }
      } catch {
        case e: Throwable => {
          gui.message("Error")
          e.printStackTrace()
          gui.alert(e.getLocalizedMessage)
          throw e
        }
      }
  }
  
}

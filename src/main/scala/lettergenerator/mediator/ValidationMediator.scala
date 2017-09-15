package lettergenerator
package mediator

import validators._
import formatter.{WordMLFormatter,Details,Template}

import scala.annotation.tailrec

import org.docx4j.openpackaging.packages.WordprocessingMLPackage

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
          gui.alert(e.getLocalizedMessage)
          throw e
        }
      }
  }
  
  def validateTemplate(details: Details, template: Template)(
    templForm: WordMLFormatter = new WordMLFormatter(template)): Unit = {
    val docText: String = templForm.text
    val templVal = new TemplateValidator(docText)
    val headers: List[(String,String)] = gui.fnAlsoInTemplate match {
      case true => details.headers.map(header => (header,header)).toList
      case false => details.headers.filter(_ != gui.fNameColumn)
        .map(header => (header,header)).toList
    }
    try{
      templVal.applyRecursion[String](
        headers,gui.message("Template variables are valid."),
        (msg: String) => throw new Exception(msg))
    } catch {
      case e: Throwable => {
        gui.message("Error on template validation")
        gui.message(errorMsgs(templVal).format(e.getLocalizedMessage))
        throw e
      }
    }
  }


  def fileNameIfDuplicate(
    name: String, extension: String, count: Int = 0): String = {
    getFileName(name,extension,count)
  }

  @tailrec
  private final def getFileName(
    name: String, extension: String, count: Int = 0): String = {

    val destination: String = gui.destinationFolder
    val increment = count + 1
    validatePath(destination+"/"+name+".docx") match {
      case Some(_) => 
        validatePath(destination+"/"+name+increment+".docx") match {
          case Some(_) => getFileName(name,extension,increment)
          case None => destination+"/"+name+increment+".docx"
        }
      case None => destination+"/"+name+".docx"
    }
  }
  
}

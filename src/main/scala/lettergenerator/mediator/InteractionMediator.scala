package lettergenerator
package mediator

import formatter._

import validator._
import loader._
import renderer.Wizard

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import scala.swing.MainFrame

import scala.annotation.tailrec

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


import java.util.{HashMap => JHashMap}

class InteractionMediator extends renderer.Interactor {
  private var gui: Wizard = _ 
  private val pathValidator = new PathValidator()
  
  def registerInterface(gui: MainFrame): Unit = this.gui = gui.asInstanceOf[Wizard]

  def hasGui: Boolean = Option[MainFrame](gui) match {
    case Some(_) => true
    case None => false
  }
  
  def runInterface(): Unit = gui.visible = true
  
  //private def messageUser(text: String): Unit = gui.message(text)
  private val messageUser: String => Unit = (text: String) => gui.message(text)
  

  def submit(): Unit = {
    messageUser("Processing...")
    Future { validatePaths() }
  }
  

  private def validatePaths(): Unit = {
    val paths = List[(String,String)](
      "details file" -> gui.detailsFile,
      "template file" -> gui.templateFile,
      "destination folder" -> gui.destinationFolder
    )

    val message = "Could not reach the %s. Please check if path is correct"+
      ", or report this issue"
    
    pathValidator.applyRecursion[String]( paths,
      loadDetails(DetailsFormatter(CsvInput(gui.detailsFile))), messageUser)
  }
  

  private def loadDetails(form: DetailsFormatter): Unit = {
    val details: List[Map[String,String]] = form.details

    val detailsMessage = "Details file error: the row with values "+
      "%s is incomplete. Please check it and try again" 
      
    validateDetails(details,new DetailsValidator(form.headers), detailsMessage)
  }


  private def validateDetails(details: List[Map[String,String]],
    validator: DetailsValidator, message: String): Unit = {
    
    var flag = false
    try {
      for (mapElement <- details) 
        validator.applyRecursion[Map[String,String]](
          List((mapElement.values.mkString(" "),mapElement)), 
          flag = true,
          (msg: String) => throw new Exception(
              "row containing '%s' has a missing value".format(msg)))
    } catch {
      case e: Throwable => {
        gui.message("Error")
        e.printStackTrace()
        //gui.alert(e.getStackTrace.mkString("\n"))
        gui.alert(e.getLocalizedMessage)
      }
    }
    if(flag) loadTemplate(details)
  }


  private def loadTemplate(details: List[Map[String,String]]): Unit = {
    val docPack: WordprocessingMLPackage = 
      TemplateFormatter(DocxInput(gui.templateFile))
        .template
        
    validateTemplate(details, docPack)
  }
  

  private def validateTemplate(details: List[Map[String,String]], 
    docPack: WordprocessingMLPackage): Unit = {
    val docText: String = WordMLToStringFormatter(docPack).text
    val validator = new TemplateValidator(docText)
    val message: String = "Error: could not find variable %s on template."
    val headers: List[(String,String)] = gui.fnAlsoInTemplate match {
      case true => details.head.keySet.map(header => (header,header)).toList
      case false => details.head.keySet.filter(_ != gui.fNameColumn)
        .map(header => (header,header)).toList
    }
    try{
      validator.applyRecursion[String](
        headers,generateLetters(details,docPack),
        (msg: String) => throw new Exception(msg))
    } catch {
      case e: Throwable => {
        e.printStackTrace()
        messageUser(message.format(e.getLocalizedMessage))
      }
    }
  }
  

  private def generateLetters(details: List[Map[String,String]],
    docPack: WordprocessingMLPackage): Unit = {
    import scala.collection.JavaConverters._

    val destination: String = gui.destinationFolder
    val template: MainDocumentPart = docPack.getMainDocumentPart
    val duplFileChecker = new PathValidator()

    @tailrec
    def fileName(name: String, counter: Int): String = {
      val increment = counter + 1
      if (duplFileChecker.validate(destination+"/"+name+".docx")) {
        if (duplFileChecker.validate(destination+"/"+name+increment+".docx")) 
          fileName(name,increment)
        else destination+"/"+name+increment+".docx"
      } else destination+"/"+name+".docx"
    }

    for(smap <- details) {
      val fname = smap.collectFirst({
        case (k: String,v: String) if k == gui.fNameColumn => v
      }) match {
        case Some(file) => file
        case None => "Output"
      }
      
      val map: JHashMap[String,String] = gui.fnAlsoInTemplate match {
        case true => new JHashMap(smap.asJava)
        case false => new JHashMap(smap.filter(_._1 != gui.fNameColumn).asJava)
      }

      val jaxbElement = template.getJaxbElement
      val xml: String = XmlUtils.marshaltoString(jaxbElement, true)
      val replaced: Object = XmlUtils.unmarshallFromTemplate(xml, map)
      template.setJaxbElement(replaced.asInstanceOf[Document])
      
      new SaveToZipFile(docPack).save(s"${fileName(fname,0)}")
      template.setJaxbElement(jaxbElement)
    }
    messageUser("Done!")
  }
  
  def detailsFileHeaders(): List[String] = {
    val message = "Could not reach the %s. Please check if path is correct"+
      ", or report this issue"
    val path: List[(String,String)] = List((message.format("details file"), gui.detailsFile))

    var columns = List[String]()

    pathValidator.applyRecursion[String](path, columns = DetailsFormatter(
      CsvInput(path.head._2)).details.head.keySet.toList, messageUser(_))
    
    List("") ++ columns
  }
}
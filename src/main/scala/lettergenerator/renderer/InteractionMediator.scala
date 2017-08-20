package lettergenerator
package renderer

import formatter._

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage  
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import scala.swing.MainFrame

import java.util.{HashMap => JHashMap}

import scala.annotation.tailrec

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class InteractionMediator() {
  private var gui: Wizard = _ 
  
  def registerInterface(gui: MainFrame): Unit = this.gui = gui.asInstanceOf[Wizard]

  def hasGui: Boolean = Option[MainFrame](gui) match {
    case Some(_) => true
    case None => false
  }
  
  def runInterface(): Unit = gui.visible = true
  
  def messageUser(text: String): Unit = gui.message(text)
  

  def submit(): Unit = {
    messageUser("Processing...")
    Future { validatePaths(PathValidator()) }
  }
  

  def validatePaths(validator: Validator ): Unit = {
    val paths = List[(String,String)](
      "details file" -> gui.detailsFile,
      "template file" -> gui.templateFile,
      "destination folder" -> gui.destinationFolder
    )

    val message = "Could not reach the %s. Please check if path is correct"+
      ", or report this issue"
    
    vldt[String](paths,validator,loadDetails(),message)
  }
  

  def loadDetails(): Unit = {
    val form = DetailsFormatter(CsvInput(gui.detailsFile))
    val details: List[Map[String,String]] = form.details

    val detailsMessage = "Details file error: the row with values "+
      "%s is incomplete. Please check it and try again" 
      
    validateDetails(details,DetailsValidator(form.headers), detailsMessage)
  }


  def validateDetails(details: List[Map[String,String]],
      validator: DetailsValidator, message: String): Unit = {
    
    var flag = false
    try {
      for (mapElement <- details) 
        vldt[Map[String,String]](
          List((mapElement.values.mkString(" "),mapElement)), 
          validator, 
          flag = true,
          message)
    } catch {
      case e: Throwable => {
        gui.message("Error")
        e.printStackTrace()
        gui.alert(e.getStackTrace.mkString("\n"))
      }
    }
    if(flag) loadTemplate(details)
  }


  def loadTemplate(details: List[Map[String,String]]): Unit = {
    val docPack: WordprocessingMLPackage = 
      TemplateFormatter(DocxInput(gui.templateFile))
        .template
        
    validateTemplate(details, docPack)
  }
  

  def validateTemplate(details: List[Map[String,String]], 
      docPack: WordprocessingMLPackage): Unit = {
    val docText: String = WordMLToStringFormatter(docPack).text
    val validator = TemplateValidator(docText)
    val message: String = "Error: could not find variable %s on template."
    val headers: List[(String,String)] = gui.fnAlsoInTemplate match {
      case true => details.head.keySet.map(header => (header,header)).toList
      case false => details.head.keySet.filter(_ != gui.fNameColumn)
        .map(header => (header,header)).toList
    }
    vldt[String](headers,validator,generateLetters(details,docPack),message)
  }
  

  def generateLetters(details: List[Map[String,String]],
      docPack: WordprocessingMLPackage): Unit = {
    import scala.collection.JavaConverters._

    val destination: String = gui.destinationFolder
    val template: MainDocumentPart = docPack.getMainDocumentPart
    val duplFileChecker = PathValidator()

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

  @tailrec
  private def vldt[A](p: List[(String,A)], validator: Validator,
      op:  => Unit, message: String): Unit = p match {
    case Nil => 
      throw new IllegalArgumentException("argument p cannot be an empty list")

    case x :: Nil => validator.validate(x._2) match {
      case true => op
      case false => messageUser(message.format(x._1))
    }

    case x :: xs => validator.validate(x._2) match {
      case true => vldt(xs,validator,op,message)
      case false => messageUser(message.format(x._1))
    }
  }
  
  def columnsForFileName(): List[String] = {
    val path: List[(String,String)] = List(("details file", gui.detailsFile))
    val validator = PathValidator()
    val message = "Could not reach the %s. Please check if path is correct"+
      ", or report this issue"

    var columns = List[String]()

    vldt[String](path, validator, columns = DetailsFormatter(
      CsvInput(path.head._2)).details.head.keySet.toList, message)
    
    List("") ++ columns

  }
  
  
}

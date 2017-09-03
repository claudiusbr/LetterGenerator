package lettergenerator
package mediator

import formatter._
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
  private var validator: ValidationMediator = _
  private var loader: LoadingMediator = _
  private var generator: DocxMaker = _
  
  private val messageUser: String => Unit = (text: String) => gui.message(text)

  def registerInterface(gui: Wizard): Unit = {
    this.gui = gui
    validator = new ValidationMediator(this.gui)
    loader = new LoadingMediator(this.gui)         
    generator = new DocxMaker(this.gui)            
  }

  def hasGui: Boolean = Option[MainFrame](gui) match {
    case Some(_) => true
    case None => false
  }
  
  def runInterface(): Unit = gui.visible = true
  
  def detailsFileHeaders(): List[String] = {
    val allowEmptyFileName = List(" ")
    allowEmptyFileName ++ new DetailsFormatter(CsvInput(validator
      .validatePathOrThrow(("details file",gui.detailsFile))))
        .details.head.keySet.toList
  }
  
  def submit(): Unit = {
    messageUser("Processing...")
    Future { 
      validator.validateAllPaths()
      val details: Details = loader.loadDetails()
      validator.validateDetails(details)()
      val docPack: WordprocessingMLPackage = loader.loadTemplate()
      validator.validateDetails(details)()
      generateLetters(details.tuples,docPack)
    }
  }

  private def generateLetters(details: List[Map[String,String]],
    docPack: WordprocessingMLPackage): Unit = {
    import scala.collection.JavaConverters._

    val destination: String = gui.destinationFolder
    val template: MainDocumentPart = docPack.getMainDocumentPart
    val duplFileChecker = validator

    @tailrec
    def fileName(name: String, counter: Int): String = {
      val increment = counter + 1
      duplFileChecker.validatePath(destination+"/"+name+".docx") match {
        case Some(_) => 
          duplFileChecker.validatePath(destination+"/"+name+increment+".docx") match {
            case Some(_) => fileName(name,increment)
            case None => destination+"/"+name+increment+".docx"
          }
        case None => destination+"/"+name+".docx"
      }
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
}
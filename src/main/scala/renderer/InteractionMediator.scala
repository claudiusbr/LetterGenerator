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

case class InteractionMediator() {
  private var gui: Wizard = _ 
  
  def registerInterface(gui: MainFrame): Unit = this.gui = gui.asInstanceOf[Wizard]
  
  def runInterface(): Unit = gui.visible = true
  
  def messageUser(text: String): Unit = gui.message(text)
  
  def submit(): Unit = {
    messageUser("Processing...")
    validatePaths(PathValidator() ) 
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
    val details: List[Map[String,String]] = 
      new DetailsFormatter(CsvInput(gui.detailsFile)).details

    validateDetails(details)
    /*
    val docPack: WordprocessingMLPackage = 
      TemplateFormatter(DocxInput(gui.templateFile)).template

    val destination = gui.destinationFolder
    
    validateContent(details,docPack,destination)
    */
  }

  def validateDetails(details: List[Map[String,String]]): Unit = {
    
  }

  def validateContent(details: List[Map[String,String]], 
      docPack: WordprocessingMLPackage, destination: String): Unit = {

    val docText: String = WordMLToStringFormatter(docPack).text


    
    //vldt[Map[String,String]](,VariableValidator(),generateLetters(details,docPack,destination),)
  }
  
  def generateLetters(details: List[Map[String,String]],
      docPack: WordprocessingMLPackage, destination: String): Unit = {

    import scala.collection.JavaConverters._
    
    val template: MainDocumentPart = docPack.getMainDocumentPart

    var counter = 0

    for(smap <- details) {

      val map: JHashMap[String,String] = new JHashMap(smap.asJava)

      val jaxbElement = template.getJaxbElement
      val xml: String = XmlUtils.marshaltoString(jaxbElement, true)
      val replaced: Object = XmlUtils.unmarshallFromTemplate(xml, map)
      template.setJaxbElement(replaced.asInstanceOf[Document])
      counter += 1
      new SaveToZipFile(docPack).save(s"$destination/Output$counter.docx")
      template.setJaxbElement(jaxbElement)
    }

    messageUser("Done!")

  }


  private def vldt[A](p: List[(String,A)], validator: Validator,
      op:  => Unit, message: String): Unit = p match {
    case Nil => throw new IllegalArgumentException("argument p cannot be an empty list")

    case x :: Nil => validator.validate(x._2) match {
      case true => loadInformation()
      case false => messageUser(message.format(x._1))
    }

    case x :: xs => validator.validate(x._2) match {
      case true => vldt(xs,validator,loadInformation(),message)
      case false => messageUser(message.format(x._1))
    }
  }
}

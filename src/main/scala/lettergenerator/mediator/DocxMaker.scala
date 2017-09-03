package lettergenerator
package mediator

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import java.util.{HashMap => JHashMap}

class DocxMaker(gui: renderer.Wizard) {
  
  def makeManyDocx(details: Iterable[Map[String,String]], 
    docPack: WordprocessingMLPackage,
    valMed: ValidationMediator): Unit = {
      details.foreach(makeDocx(_,docPack,valMed))
      gui.message("Done!")
  }

  
  def makeDocx(details: Map[String,String], docPack: WordprocessingMLPackage,
    valMed: ValidationMediator): Unit = {

    val detailsAsJMap = prepareMap(details)
    val tempFileName = fileName(details)
    val finalFileName = valMed.fileNameIfDuplicate(tempFileName, ".docx")
    generateDocx(detailsAsJMap,finalFileName,docPack)
    gui.message(s"Saving $finalFileName ...")
  }

  def generateDocx(jmap: JHashMap[String,String], fileName: String, 
    docPack: WordprocessingMLPackage): Unit = {

    val template: MainDocumentPart = docPack.getMainDocumentPart

    val jaxbElement = template.getJaxbElement
    val xml: String = XmlUtils.marshaltoString(jaxbElement, true)
    val replaced: Object = XmlUtils.unmarshallFromTemplate(xml, jmap)
    template.setJaxbElement(replaced.asInstanceOf[Document])
    
    new SaveToZipFile(docPack).save(fileName)
    template.setJaxbElement(jaxbElement)
  } 
  
  def prepareMap(details: Map[String,String]): JHashMap[String,String] = {
    import scala.collection.JavaConverters._

    gui.fnAlsoInTemplate match {
      case true => new JHashMap(details.asJava)
      case false => new JHashMap(details.filter(_._1 != gui.fNameColumn).asJava)
    }
  }

  def fileName(details: Map[String,String]): String = {
    val fileNameColumn: String = gui.fNameColumn
    details.collectFirst({
      case (fileNameColumn,v: String) => v
    }) match {
      case Some(file) => file
      case None => "Output"
    }
  }
}
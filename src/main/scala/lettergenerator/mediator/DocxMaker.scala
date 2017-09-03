package lettergenerator
package mediator

import formatter.DocxMakerFormatter

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import java.util.{HashMap => JHashMap}

class DocxMaker(gui: renderer.Wizard) {
  val formatter = new DocxMakerFormatter
  
  def makeManyDocx(details: Iterable[Map[String,String]], 
    docPack: WordprocessingMLPackage,
    valMed: ValidationMediator): Unit = {
      details.foreach(makeDocx(_,docPack,valMed))
      gui.message("Done!")
  }
  
  def makeDocx(details: Map[String,String], docPack: WordprocessingMLPackage,
    valMed: ValidationMediator): Unit = {

    val detailsAsJMap = formatter.prepareMap(
        details,gui.fnAlsoInTemplate,gui.fNameColumn)

    val tempFileName = formatter.fileName(details,gui.fNameColumn)
    val finalFileName = valMed.fileNameIfDuplicate(tempFileName, ".docx")
    generateDocx(detailsAsJMap,finalFileName,docPack)
    gui.message(s"Saving $finalFileName ...")
  }

  private def generateDocx(jmap: JHashMap[String,String], fileName: String, 
    docPack: WordprocessingMLPackage): Unit = {

    val template: MainDocumentPart = docPack.getMainDocumentPart

    val jaxbElement = template.getJaxbElement
    val xml: String = XmlUtils.marshaltoString(jaxbElement, true)
    val replaced: Object = XmlUtils.unmarshallFromTemplate(xml, jmap)
    template.setJaxbElement(replaced.asInstanceOf[Document])
    
    new SaveToZipFile(docPack).save(fileName)
    template.setJaxbElement(jaxbElement)
  } 
}
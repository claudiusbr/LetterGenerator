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
  
  def makeManyDocx(details: Details, 
    docPack: WordprocessingMLPackage, valMed: ValidationMediator)(
    saver: SaveToZipFile = new SaveToZipFile(docPack)): Unit = {

    details.tuples.foreach(makeSingleDocx(_,docPack,valMed)(saver))
    gui.message("Done!")
  }
  
  def makeSingleDocx(detailsTuple: Map[String,String], 
    docPack: WordprocessingMLPackage, valMed: ValidationMediator)(
    saver: SaveToZipFile = new SaveToZipFile(docPack)): Unit = {

    val detailsAsJMap = formatter.prepareMap(
        detailsTuple,gui.fnAlsoInTemplate,gui.fNameColumn)

    val tempFileName = formatter.fileName(detailsTuple,gui.fNameColumn)
    val finalFileName = valMed.fileNameIfDuplicate(tempFileName, ".docx")
    gui.message(s"Saving $finalFileName ...")
    draftSaveReset(detailsAsJMap,finalFileName,docPack)(saver)
  }

  private def draftSaveReset(jmap: JHashMap[String,String], fileName: String, 
    docPack: WordprocessingMLPackage)(saver: SaveToZipFile): Unit = {

    val marshaller = XmlUtils.marshaltoString(_: Any, true)
    /* draft */
    // this returns a reference to the Main Document Part, I think
    val template: MainDocumentPart = docPack.getMainDocumentPart

    // which is why we go through the trouble of doing the below
    // now, if the above is actually a copy and not a reference
    // then we shouldn't bother with the 'reset' bit
    val jaxbElement: Document = template.getJaxbElement
    val xml: String = XmlUtils.marshaltoString(jaxbElement, true)
    val replaced: Object = XmlUtils.unmarshallFromTemplate(xml, jmap)
    template.setJaxbElement(replaced.asInstanceOf[Document])
    
    // save
    saver.save(fileName)
    
    // reset
    template.setJaxbElement(jaxbElement)
  } 
}
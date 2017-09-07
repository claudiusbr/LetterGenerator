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
    val template: MainDocumentPart = getTemplateMainPart(docPack)
    val original: Document = getTemplateContents(template)
    val newContents: Document = makeNewMainPartContents(original,detailsAsJMap)
    setNewMainPartContents(template,newContents)
    saveNewDocx(docPack,finalFileName)(saver)
    resetTemplateToOriginal(template, original)
  }

  private def getTemplateMainPart(docPack: WordprocessingMLPackage): MainDocumentPart = {
    docPack.getMainDocumentPart
  }

  private def getTemplateContents(template: MainDocumentPart): Document = {
    template.getJaxbElement
  }

  private def makeNewMainPartContents(original: Document,
    jmapDetails: JHashMap[String,String]): Document = {

    val xml: String = XmlUtils.marshaltoString(original, true)
    XmlUtils.unmarshallFromTemplate(xml, jmapDetails).asInstanceOf[Document]
  }

  private def setNewMainPartContents(template: MainDocumentPart, replacement: Document): Unit = {
    template.setJaxbElement(replacement)
  }

  private def saveNewDocx(docPack: WordprocessingMLPackage, fileName: String)(
    saver: SaveToZipFile = new SaveToZipFile(docPack)): Unit = {
    saver.save(fileName)
  }

  private def resetTemplateToOriginal(template: MainDocumentPart, original: Document): Unit = {
    template.setJaxbElement(original)
  }
}

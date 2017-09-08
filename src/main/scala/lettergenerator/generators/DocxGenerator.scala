package lettergenerator
package generators

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import java.util.{HashMap => JHashMap}

/**
 *
 */
class DocxGenerator(docPack: WordprocessingMLPackage) {

  def generate(jmapDetails: JHashMap[String,String], fileName: String)(
    saver: SaveToZipFile = new SaveToZipFile(docPack)): Unit = {

    val template: MainDocumentPart = getTemplateMainPart
    val original: Document = getTemplateContents(template)
    val newContents: Document = makeNewMainPartContents(original,jmapDetails)
    setNewMainPartContents(template,newContents)
    saveNewDocx(fileName)(saver)
    resetTemplateToOriginal(template, original)
  }

  private def getTemplateMainPart: MainDocumentPart = {
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

  private def saveNewDocx(fileName: String)(saver: SaveToZipFile): Unit = {
    saver.save(fileName)
  }

  private def resetTemplateToOriginal(template: MainDocumentPart, original: Document): Unit = {
    template.setJaxbElement(original)
  }
}

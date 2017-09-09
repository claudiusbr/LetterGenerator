package lettergenerator
package generators

import formatter.Template

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import java.util.{HashMap => JHashMap}

class DocxGenerator(template: Template) {

  def generate(jmapDetails: JHashMap[String,String], fileName: String)(
    saver: SaveToZipFile = new SaveToZipFile(template.docPack)): Unit = {

    val templateMainPart: MainDocumentPart = template.mainDocumentPart
    val original: Document = template.jaxbElement
    val newContents: Document = makeNewMainPartContents(original,jmapDetails)
    setNewMainPartContents(templateMainPart,newContents)
    saveNewDocx(fileName)(saver)
    resetTemplateToOriginal(templateMainPart, original)
  }

  private def makeNewMainPartContents(original: Document,
    jmapDetails: JHashMap[String,String]): Document = {

    val templateText: String = XmlUtils.marshaltoString(original, true)
    XmlUtils.unmarshallFromTemplate(templateText, jmapDetails).asInstanceOf[Document]
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

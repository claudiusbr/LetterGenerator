package lettergenerator
package formatter

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

class Template(val docPack: WordprocessingMLPackage) {
  val mainDocumentPart: MainDocumentPart = docPack.getMainDocumentPart

  def jaxbElement: Document  = mainDocumentPart.getJaxbElement

  def jaxbElement_= (replacement: Document): Unit = 
    mainDocumentPart.setJaxbElement(replacement)
}

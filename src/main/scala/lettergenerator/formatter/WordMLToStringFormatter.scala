package lettergenerator
package formatter

import org.docx4j.XmlUtils
import org.docx4j.openpackaging.packages.WordprocessingMLPackage

case class WordMLToStringFormatter(docPack: WordprocessingMLPackage) {
  val text: String = XmlUtils.marshaltoString(docPack.getMainDocumentPart.getJaxbElement,true)
}

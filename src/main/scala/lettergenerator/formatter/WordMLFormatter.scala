package lettergenerator
package formatter

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.packages.WordprocessingMLPackage

import java.util.{HashMap => JHashMap}

class WordMLFormatter(template: Template) {
  val text: String = XmlUtils.marshaltoString(template.jaxbElement,true)

  def replaceTextVariablesWith(jmapDetails: JHashMap[String,String]): Document = 
    XmlUtils.unmarshallFromTemplate(text, jmapDetails).asInstanceOf[Document]
}

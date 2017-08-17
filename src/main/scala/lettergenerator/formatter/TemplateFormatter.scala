package lettergenerator
package formatter

import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

case class TemplateFormatter(input: Input) {
  
  val template: WordprocessingMLPackage = anyToWordPack(input.getContents())
  
  def anyToWordPack(obj: Any): WordprocessingMLPackage = 
    obj.asInstanceOf[WordprocessingMLPackage]
}

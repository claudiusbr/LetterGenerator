package lettergenerator
package formatter

import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import loader._

class TemplateFormatter(input: Input) {
  
  val template: WordprocessingMLPackage = anyToWordPack(input.getContents())
  
  private def anyToWordPack(obj: Any): WordprocessingMLPackage = 
    obj.asInstanceOf[WordprocessingMLPackage]
}

package lettergenerator
package formatter

import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import loader._

/**
 * formatting here consists only of type casting it to the right format
 */
class TemplateFormatter(input: Input) {
  
  val template: Template = new Template(anyToWordPack(input.getContents()))
  
  private def anyToWordPack(obj: Any): WordprocessingMLPackage = 
    obj.asInstanceOf[WordprocessingMLPackage]
}

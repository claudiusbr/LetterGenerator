package lettergenerator
package loader

import org.docx4j.openpackaging.packages.WordprocessingMLPackage

case class DocxInput(fileName: String) extends Input {
  override def getContents(): Any = 
    WordprocessingMLPackage.load(new java.io.File(fileName))
}

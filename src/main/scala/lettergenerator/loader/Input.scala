package lettergenerator
package loader

import org.docx4j.openpackaging.packages.WordprocessingMLPackage

trait Input {
  def getContents(): Any
}

case class CsvInput(fileName: String) extends Input {
  override def getContents(): Any = 
    io.Source.fromFile(fileName).getLines // Iterator[String]

}

case class DocxInput(fileName: String) extends Input {

  override def getContents(): Any = 
    WordprocessingMLPackage.load(new java.io.File(fileName))
}

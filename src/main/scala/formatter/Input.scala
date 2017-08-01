package formatter

trait Input {
  def getContents(): Any
}

class CsvInput(fileName: String) extends Input {
  override def getContents(): Any = 
    io.Source.fromFile(fileName).getLines

}

class DocxInput(fileName: String) extends Input {
  import org.docx4j.openpackaging.packages.WordprocessingMLPackage

  override def getContents(): Any = 
    WordprocessingMLPackage.load(new java.io.File(fileName))
}
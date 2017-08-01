package formatter

trait Input {
  def getContents(): Iterator[String]
  def getFile(): Any
}

class CsvInput(fileName: String) extends Input {
  override def getContents(): Iterator[String] = 
    io.Source.fromFile(fileName).getLines

  override def getFile(): Any = getContents()
}

class DocxInput(fileName: String) extends Input {
  import org.docx4j.openpackaging.packages.{WordprocessingMLPackage => WordPack}
  


  override def getContents(): Iterator[String] = ???
  override def getFile(): Any = ???
}
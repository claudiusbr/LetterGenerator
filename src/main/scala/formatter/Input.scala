package formatter

trait Input {
  def getContents(): Iterator[String]
}

class CsvInput(file: String) extends Input {
  override def getContents(): Iterator[String] = {
      io.Source.fromFile(file).getLines
  }
}
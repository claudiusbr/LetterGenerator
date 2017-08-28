package lettergenerator
package loader

case class CsvInput(fileName: String) extends Input {
  override def getContents(): Any = 
    io.Source.fromFile(fileName).getLines // Iterator[String]
}


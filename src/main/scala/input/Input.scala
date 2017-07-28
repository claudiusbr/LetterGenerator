package input

import java.io.File

trait Input {
  def getContents(fileName: String)
}

class CsvInput(file: File) extends Input {
  // Open file
  // Get Input
  // close file
  // return input as a collection
  override def getContents(fileName: String) = ???
}
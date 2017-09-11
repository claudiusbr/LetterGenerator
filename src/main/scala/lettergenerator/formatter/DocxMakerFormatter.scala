package lettergenerator
package formatter

import java.util.{HashMap => JHashMap}

class DocxMakerFormatter {

  /**
   * returns the details tuple Map as a JHashMap, filtering out
   * the file name column if it is provided
   */
  def prepareMap(detailsTuple: Map[String,String],
    columnNametoFilterOut: String): JHashMap[String,String] = {

    import scala.collection.JavaConverters._

    new JHashMap(detailsTuple.filter(_._1 != columnNametoFilterOut).asJava)

  }
  
  /**
   * Returns the file name provided by the user in the details
   * file for a specific tuple. If no column is provided for the 
   * file name, it returns the string 'Output'.
   */
  def fileName(detailsTuple: Map[String,String], fileNameColumn: String): String = {
    detailsTuple.collectFirst({
      case (column: String, value: String) if column == fileNameColumn => value
    }) match {
      case Some(file) => file
      case None => "Output"
    }

  }
}
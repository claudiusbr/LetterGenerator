package lettergenerator.formatter

import java.util.{HashMap => JHashMap}

class DocxMakerFormatter {

  def prepareMap(details: Map[String,String],
    fileNameAlsoInTemplate: Boolean,
    fileNameColumn: String): JHashMap[String,String] = {
    import scala.collection.JavaConverters._

    fileNameAlsoInTemplate match {
      case true => new JHashMap(details.asJava)
      case false => new JHashMap(details.filter(_._1 != fileNameColumn).asJava)
    }
  }
  
  def fileName(details: Map[String,String],
    fileNameColumn: String): String = details.collectFirst({
      case (k: String,v: String) if k == fileNameColumn => v
    }) match {
      case Some(file) => file
      case None => "Output"
    }
  
}
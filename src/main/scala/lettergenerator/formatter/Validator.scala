package lettergenerator
package formatter

//import scala.collection.JavaConverters._

import scala.language.implicitConversions

trait Validator {
  def validate(what: Any): Boolean
}

case class PathValidator() extends Validator {
  import java.nio.file.{Paths,Files}
  import Converters.anyToString
  
  def validate(what: Any): Boolean = validatePath(what)

  def validatePath(path: String): Boolean = {
    if (path.nonEmpty) Files.exists(Paths.get(path))
    else false
  }
}

case class DetailsValidator(headers: Array[String]) extends Validator {
  import Converters.anyToDetMap  

  def validate(what: Any): Boolean = validateDetailsNotEmpty(what)

  def validateDetailsNotEmpty(details: Map[String,String]): Boolean = {
    for (header <- headers) 
      if(!details.keySet.contains(header)) return false

    for (kv <- details) {
      if (kv._2.isEmpty) return false 
    }
    true
  }
}

case class TemplateValidator(text: String) extends Validator {
  def validate(what: Any): Boolean = {
    val detailsHeader = what.asInstanceOf[String]
    validateVariables(detailsHeader)
  }
  
  def validateVariables(variableName: String): Boolean = {
    if (text.contains("${"+variableName+"}")) return true
    false
  }
}

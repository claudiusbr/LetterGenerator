package lettergenerator
package validator

import scala.language.implicitConversions

trait Validator {
  def validate(what: Any): Boolean
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

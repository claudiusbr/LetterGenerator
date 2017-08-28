package lettergenerator
package validator

import scala.language.implicitConversions

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


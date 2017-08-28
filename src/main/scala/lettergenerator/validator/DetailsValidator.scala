package lettergenerator
package validator

import scala.language.implicitConversions

/**
 * This class is responsible for validating the details file
 * @param headers an array containing the column headers of the details	file
 */
class DetailsValidator(headers: Array[String]) extends RecursiveValidator {
  import Converters.anyToDetMap  

  override def validate(what: Any): Boolean = validateDetailsNotEmpty(what)

  /**
   * This method iterates through the details' file tuples and
   * asserts that there are no empty values 
   * @param details a map of all the values to be checked
   */
  def validateDetailsNotEmpty(details: Map[String,String]): Boolean = {
    for (header <- headers) 
      if(!details.keySet.contains(header)) return false

    for (kv <- details) {
      if (kv._2.isEmpty) return false 
    }
    true
  }
}


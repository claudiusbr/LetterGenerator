package lettergenerator
package validators

case class TemplateValidator(text: String) extends RecursiveValidator {
  def validate(what: Any): Boolean = {
    val detailsHeader = what.asInstanceOf[String]
    validateVariables(detailsHeader)
  }
  
  def validateVariables(variableName: String): Boolean = {
    if (text.contains("${"+variableName+"}")) return true
    false
  }
}

package lettergenerator
package validator

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

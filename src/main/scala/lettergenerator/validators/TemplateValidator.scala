package lettergenerator
package validators

case class TemplateValidator(text: String) extends RecursiveValidator {
  println(text)
  def validate(what: Any): Boolean = {
    val detailsHeader = what.asInstanceOf[String]
    validateVariables(detailsHeader)
  }
  
  def validateVariables(variableName: String): Boolean = {
    text.contains("${"+variableName+"}")
  }
}

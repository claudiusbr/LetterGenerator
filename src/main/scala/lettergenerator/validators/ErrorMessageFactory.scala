package lettergenerator
package validators

object ErrorMessageFactory {
  def apply(validator: Validator): String = validator match {
    case PathValidator() => "Could not reach the %s. Please" +
      " check if path is correct, or report this issue"
    
    case DetailsValidator(_, _) => "Details file error: the row" + 
      " with values %s is incomplete. Please check it and try again"

    case TemplateValidator(_) => "Error: could not find variable %s on template."
  }
}

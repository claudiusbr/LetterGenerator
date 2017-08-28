package lettergenerator
package validator

trait Validator {
  def validate(what: Any): Boolean
}

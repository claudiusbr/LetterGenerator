package lettergenerator
package validators

trait Validator {
  def validate(what: Any): Boolean
}

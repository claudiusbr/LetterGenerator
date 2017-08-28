package lettergenerator.renderer

/**
 * This trait defines the methods used by Wizard to
 * interact with the rest of the application
 */
trait Interactor {
  /**
   * the method called when the submit() button is pressed
   */
  def submit(): Unit
  
  /**
   * this method should return a List[String] with 
   * the column header names of the details file
   */
  def detailsFileHeaders(): List[String] 
}

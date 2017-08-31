package lettergenerator
package mediator

import loader._
import formatter._

class LoadingMediator(gui: renderer.Wizard) {

  def loadDetails(form: DetailsFormatter = new DetailsFormatter(
    new CsvInput(gui.detailsFile))): List[Map[String,String]] = form.details
}
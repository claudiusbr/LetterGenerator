package lettergenerator
package mediator

import loader._
import formatter._

import org.docx4j.openpackaging.packages.WordprocessingMLPackage

class LoadingMediator(gui: renderer.Wizard) {

  def loadDetails(form: DetailsFormatter = new DetailsFormatter(
    new CsvInput(gui.detailsFile))): Details = {
      new Details(form.headers,form.details)
  }
    
  def loadTemplate(form: TemplateFormatter = new TemplateFormatter(
    new DocxInput(gui.templateFile))): WordprocessingMLPackage = 
      form.template
}
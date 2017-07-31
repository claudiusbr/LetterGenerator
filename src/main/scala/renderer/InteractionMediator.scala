package renderer

import input._

import scala.swing.MainFrame

case class InteractionMediator() {
  def runInterface(ui: MainFrame): Unit = {
    ui.visible = true
  }
  
  def submit(ui: MainFrame): Unit = {
    val gui = ui.asInstanceOf[Wizard]
    gui.message.text = "Processing..."
    validatePaths(gui) 
  }
  
  def validatePaths(gui: Wizard): Unit = {
    val validator = PathValidator() 

    if (validator.validate(gui.detailsFile)) {
      if (validator.validate(gui.templateFile)) {
        if(validator.validate(gui.destinationFolder)) {
          loadDetails(gui)
        } else {
          gui.message.text = 
            "Could not reach the destination folder. Please check if it is correct, or report this issue."
        }
      } else {
        gui.message.text = 
          "Could not reach the template file. Please check if it is correct, or report this issue."
      }
    } else {
      gui.message.text = 
        "Could not reach the details file. Please check if it is correct, or report this issue."
    }
  }
  
  def loadDetails(gui: Wizard): Unit = {
    
  }
}
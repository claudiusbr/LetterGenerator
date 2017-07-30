package renderer

import scala.swing.MainFrame

case class InteractionMediator() {
  def runInterface(ui: MainFrame): Unit = {
    ui.visible = true
  }
  
  def generateLetters(ui: MainFrame): Unit = {
    val gui = ui.asInstanceOf[Wizard]
    // test
    println(gui.detailsFile)
    println(gui.templateFile)
    println(gui.destinationFolder)
    gui.message.text = "Done!"
  }
}
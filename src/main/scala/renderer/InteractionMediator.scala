package renderer

import scala.swing.MainFrame

case class InteractionMediator() {
  def runInterface(): Unit = {
    val ui: MainFrame = new Wizard
    ui.visible = true
  }

}
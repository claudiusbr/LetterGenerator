package renderer

import scala.swing.MainFrame

case class InteractionMediator(ui: MainFrame) {
  def runInterface(): Unit = {
    ui.visible = true
  }
}
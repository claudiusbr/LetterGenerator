package lettergenerator.mediator

import lettergenerator._

abstract class UserMessenger(gui: renderer.Wizard) {

  protected val messageUser: String => Unit = (text: String) => gui.message(text)

  protected def alertUser(text: String): Unit = gui.alert(text)
}
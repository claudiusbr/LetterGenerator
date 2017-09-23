package lettergenerator

object Runner extends App {
  val medium = new mediator.InteractionMediator()
  val wiz = new renderer.Wizard(medium)
  wiz.setLayout("Letter Generator")
  medium.registerInterface(wiz)
  medium.runInterface()
}

package lettergenerator

object Runner extends App {
  val medium = new mediator.InteractionMediator()
  medium.registerInterface(new renderer.Wizard(medium))
  medium.runInterface()
}

import renderer.{InteractionMediator, Wizard}

object Runner extends App {
  val medium = InteractionMediator()
  medium.runInterface(new Wizard(medium))
}
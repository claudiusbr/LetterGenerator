import renderer.{InteractionMediator, Wizard}

object Runner extends App {
  val medium = InteractionMediator(new Wizard)
  medium.runInterface()
}
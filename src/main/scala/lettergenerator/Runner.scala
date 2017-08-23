package lettergenerator
import renderer.{InteractionMediator, Wizard}

object Runner extends App {
  val medium = new InteractionMediator()
  medium.registerInterface(new Wizard(medium))
  medium.runInterface()
}

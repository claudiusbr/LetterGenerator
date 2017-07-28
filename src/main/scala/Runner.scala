import renderer.InteractionMediator


object Runner extends App {
  val medium: InteractionMediator = InteractionMediator()
  medium.runInterface()
}

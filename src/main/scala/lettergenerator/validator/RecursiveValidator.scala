package lettergenerator.validator

import scala.annotation.tailrec

abstract class RecursiveValidator extends Validator {
  
  def applyRecursion[A](
    p: List[(String,A)], 
    runIfValid: => Unit, 
    messageIfNot: String => Unit): Unit = p match {

    case Nil => 
      throw new IllegalArgumentException("argument p cannot be an empty list")

    case x :: Nil => this.validate(x._2) match {
      case true => runIfValid
      case false => messageIfNot(x._1)
    }

    case x :: xs => this.validate(x._2) match {
      case true => applyRecursion(xs,runIfValid,messageIfNot)
      case false => messageIfNot(x._1)
    }
  }
  
}
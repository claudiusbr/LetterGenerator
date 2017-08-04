package formatter

import scala.language.implicitConversions

object Converters {
  implicit def anyToString(what: Any): String = 
    what.asInstanceOf[String]
  
  implicit def anyToDetMap(what: Any): Seq[Map[String,String]] = 
    what.asInstanceOf[Seq[Map[String,String]]]
  
  implicit def mapAsJavaMap[A, B](m: Map[A, B]): java.util.HashMap[A, B] = m match {
    case null                 => null
    case (wrapped: Map[A,B]) => wrapped.asInstanceOf[java.util.HashMap[A, B]]
  }
}
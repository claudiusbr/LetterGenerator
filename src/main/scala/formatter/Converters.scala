package formatter

import scala.language.implicitConversions

import java.util.{HashMap => JHashMap}

object Converters {
  implicit def anyToString(what: Any): String = 
    what.asInstanceOf[String]
  
  implicit def anyToDetMap(what: Any): Seq[Map[String,String]] = 
    what.asInstanceOf[Seq[Map[String,String]]]
}
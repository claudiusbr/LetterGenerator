package formatter

import scala.language.implicitConversions

import java.util.{HashMap => JHashMap}

object Converters {
  implicit def anyToString(what: Any): String = 
    what.asInstanceOf[String]
  
  implicit def anyToDetMap(what: Any): Map[String,String] = 
    what.asInstanceOf[Map[String,String]]

  implicit def fromAnyToIterator(par: Any): Iterator[String] = 
    par.asInstanceOf[Iterator[String]]
}
package lettergenerator

import scala.language.implicitConversions

object Converters {
  implicit def anyToString(what: Any): String = 
    what.asInstanceOf[String]
  
  implicit def anyToDetMap(what: Any): Map[String,String] = 
    what.asInstanceOf[Map[String,String]]

  implicit def fromAnyToIterator(par: Any): Iterator[String] = 
    par.asInstanceOf[Iterator[String]]
}

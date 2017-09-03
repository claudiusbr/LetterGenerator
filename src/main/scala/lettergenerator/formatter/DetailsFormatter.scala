package lettergenerator
package formatter

import loader._

import scala.language.implicitConversions

import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import scala.util.matching.Regex
import lettergenerator.Converters

class DetailsFormatter(input: Input) {
  
  private val Separator: String = ";SEPARATOR;"

  import Converters.fromAnyToIterator
  val it: Iterator[String] = input.getContents()

  val headers: Array[String] = it.next.split(",")

  val details: List[Map[String,String]] = {
    
    val det: ListBuffer[Map[String,String]] = ListBuffer()

    // fill up columns with values from input
    while (it.hasNext) {
      val tuple: Array[String] = replCommas(it.next).split(",")

      val map = new mutable.HashMap[String,String]()

      tuple.indices.foreach(i => map(headers(i)) = tuple(i)
          .replaceAll(s"$Separator",",")
          .replaceAll("\"",""))
      
      det += map.toMap
    }

    det.toList
  }
  
  private def replCommas(text: String): String = {
    // this is needed to avoid wrong splitting of any line
    val commaInText: Regex = "(.*\".*),(.*\".*)".r

    // this is where the regular expression comes in
    val formatted = commaInText.replaceAllIn(text, _ => s"$$1$Separator$$2")

    commaInText.findFirstIn(formatted) match {
      case Some(e) => replCommas(e)
      case None => formatted
    }
  }

}

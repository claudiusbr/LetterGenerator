package formatter

import scala.collection.mutable.{HashMap => mHashMap,ArrayBuffer}
import scala.collection.immutable.HashMap
import scala.util.matching.Regex

class DetailsFormatter(input: Input) {

  val details: Map[String,Array[String]] = {

    /* implicitly converted to Iterator */
    val it: Iterator[String] = input.getContents()

    val det: mHashMap[String,ArrayBuffer[String]] = mHashMap()

    val headers: Array[String] = it.next.split(",")

    for (element <- headers) {
      det(element) = ArrayBuffer[String]()
    }

    // fill up columns with values from input
    while (it.hasNext) {
      val tuple: Array[String] = replCommas(it.next).split(",")

      for (i <- tuple.indices) det(headers(i)) += tuple(i)
    }

    det.map(e => (e._1, e._2.toArray)).toMap
  }
  
  def replCommas(text: String): String = {
    // this is needed to avoid wrong splitting of any line
    val commaInText: Regex = "(.*\".*),(.*\".*)".r

    // this is where the regular expression comes in
    val formatted = commaInText.replaceAllIn(text, _ => s"$$1;$$2")

    commaInText.findFirstIn(formatted) match {
      case Some(e) => replCommas(e)
      case None => formatted
    }
  }
  
  implicit def fromAnyToIterator(par: Any): Iterator[String] = 
    par.asInstanceOf[Iterator[String]]

}
package formatter

import scala.language.implicitConversions

import java.util.{HashMap => JHashMap}

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable
import scala.util.matching.Regex

case class DetailsFormatter(input: Input) {
  
  private val Separator: String = ";SEPARATOR;"

  val details: Seq[JHashMap[String,String]] = {

    val it: Iterator[String] = input.getContents()

    val headers: Array[String] = it.next.split(",")
    
    val det: ArrayBuffer[JHashMap[String,String]] = ArrayBuffer()

    // fill up columns with values from input
    while (it.hasNext) {
      val tuple: Array[String] = replCommas(it.next).split(",")

      val map = new JHashMap[String,String]()

      tuple.indices.foreach(i => map.put(headers(i), tuple(i)
          .replaceAll(s"$Separator",",")
          .replaceAll("\"","")))
      
      det += map
    }

    det.toSeq
  }
  
  def replCommas(text: String): String = {
    // this is needed to avoid wrong splitting of any line
    val commaInText: Regex = "(.*\".*),(.*\".*)".r

    // this is where the regular expression comes in
    val formatted = commaInText.replaceAllIn(text, _ => s"$$1$Separator$$2")

    commaInText.findFirstIn(formatted) match {
      case Some(e) => replCommas(e)
      case None => formatted
    }
  }
  
  implicit def fromAnyToIterator(par: Any): Iterator[String] = 
    par.asInstanceOf[Iterator[String]]

}
package lettergenerator
package formatter

class Details(val headers: Array[String], val tuples: List[Map[String,String]]) {
  def this(tuples: List[Map[String,String]]) {
    this(tuples.head.keys.toArray,tuples)
  }
}

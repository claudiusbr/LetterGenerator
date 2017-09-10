package lettergenerator

import formatter.{Details, Template}
import renderer.Wizard

import org.scalatest.mockito.MockitoSugar

class TestObjects extends MockitoSugar {
  val tuples: List[Map[String,String]] = List(
    Map("name" -> "The Quick Brown Fox", 
      "action" -> "Jumped Over The Lazy Dog",
      "consequence" -> "earned +35XP"),

    Map("name" -> "The Lazy Dog",
        "action" -> "Was Jumped Over By The Quick Brown Fox",
        "consequence" -> "Had To Re-evaluate His Life Choices"))
        
  val headers: Array[String] = tuples.head.keys.toArray

  val details = new Details(headers, tuples) 
  
  val mockGui: Wizard = mock[Wizard]

  val mockTempl = mock[Template]
}

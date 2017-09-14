package lettergenerator


import loader.Input

import org.scalatest.mockito.MockitoSugar

class TestObjects extends MockitoSugar {
  import renderer.Wizard
  val mockGui: Wizard = mock[Wizard]
  val mockInput = mock[Input]
}

trait DetailsTestObjects {
  val tuples: List[Map[String,String]] = List(
    Map("name" -> "The Quick Brown Fox", 
      "action" -> "Jumped Over The Lazy Dog",
      "consequence" -> "earned +35XP"),

    Map("name" -> "The Lazy Dog",
        "action" -> "Was Jumped Over By The Quick Brown Fox",
        "consequence" -> "Had To Re-evaluate His Life Choices"))

  val headers: Array[String] = tuples.head.keys.toArray

  val details = new formatter.Details(headers, tuples) 
}


trait TemplateTestObjects extends MockitoSugar {

  import org.docx4j.XmlUtils
  import org.docx4j.wml.Document
  import org.docx4j.openpackaging.io.SaveToZipFile
  import org.docx4j.openpackaging.packages.WordprocessingMLPackage
  import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
  import org.mockito.Mockito.when

  val mockTempl = mock[formatter.Template]

  val mockJaxbElement = mock[Document]
  val mockNewJaxbElement = mock[Document]
  val mockMainPart = mock[MainDocumentPart]
  val mockDocPack = mock[WordprocessingMLPackage]

  when(mockDocPack.getMainDocumentPart).thenReturn(mockMainPart)
  when(mockMainPart.getJaxbElement).thenReturn(mockJaxbElement)
}
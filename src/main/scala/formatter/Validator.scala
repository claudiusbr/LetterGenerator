package formatter

//import scala.collection.JavaConverters._

import scala.language.implicitConversions

trait Validator {
  def validate(what: Any): Boolean
}

case class PathValidator() extends Validator {
  import java.nio.file.{Paths,Files}
  import Converters.anyToString
  
  def validate(what: Any): Boolean = validatePath(what)

  def validatePath(path: String): Boolean = {
    if (path.nonEmpty) Files.exists(Paths.get(path))
    else false
  }
}

case class DetailsValidator() extends Validator {
  import Converters.anyToDetMap  

  def validate(what: Any): Boolean = validateDetailsNotEmpty(what)

  def validateDetailsNotEmpty(details: Seq[Map[String,String]]): Boolean = {
    for (map <- details; kv <- map) {
      if (kv._2.isEmpty) return false 
    }
    true
  }
}

case class VariableValidator() extends Validator {
  import org.docx4j.openpackaging.packages.WordprocessingMLPackage
  
  def validate(what: Any): Boolean = {
    val (details,docPack) = what.asInstanceOf[(Seq[Map[String,String]],WordprocessingMLPackage)]
    validateVariables(details,docPack)
  }
  
  def validateVariables(details: Seq[Map[String,String]], docPack: WordprocessingMLPackage): Boolean = {
    true
  }
}
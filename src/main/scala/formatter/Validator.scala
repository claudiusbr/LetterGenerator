package formatter

sealed trait Validator {
  def validate(what: Any): Boolean
}

case class PathValidator() extends Validator {
  import java.nio.file.{Path,Paths,Files}

  def validate(what: Any): Boolean = {
    val path: String = what.asInstanceOf[String]

    if (path.nonEmpty) Files.exists(Paths.get(path))
    else false
  }
}
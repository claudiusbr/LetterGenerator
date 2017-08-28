package lettergenerator
package validators

import scala.language.implicitConversions

class PathValidator extends RecursiveValidator {
  import java.nio.file.{Paths,Files}
  import Converters.anyToString
  
  override def validate(what: Any): Boolean = validatePath(what)

  private def validatePath(path: String): Boolean = {
    if (path.nonEmpty) Files.exists(Paths.get(path))
    else false
  }
}

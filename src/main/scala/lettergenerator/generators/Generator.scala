package lettergenerator
package generators

import formatter.{WordMLFormatter, Template}

import org.docx4j.openpackaging.io.SaveToZipFile

import java.util.{HashMap => JHashMap}

abstract class Generator(template: Template) {
  def generate(jmapDetails: JHashMap[String,String], fileName: String)(
    saver: SaveToZipFile = new SaveToZipFile(template.docPack),
    formatter: WordMLFormatter = new WordMLFormatter(template)): Unit
}
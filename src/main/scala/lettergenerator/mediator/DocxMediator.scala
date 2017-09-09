package lettergenerator
package mediator

import formatter.{DocxMakerFormatter,Template,Details}
import generators.DocxGenerator

import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage

import java.util.{HashMap => JHashMap}

class DocxMediator(gui: renderer.Wizard, template: Template) {
  val formatter = new DocxMakerFormatter
  val generator = new DocxGenerator(template)
  
  def generateDocx(details: Details, valMed: ValidationMediator)(
    saver: SaveToZipFile = new SaveToZipFile(template.docPack)): Unit = {

    details.tuples.foreach(generateDocx(_,valMed)(saver))
  }
  
  def generateDocx(detailsTuple: Map[String,String], 
    valMed: ValidationMediator)(saver: SaveToZipFile): Unit = {

    val detailsAsJMap = formatter.prepareMap(
        detailsTuple,gui.fnAlsoInTemplate,gui.fNameColumn)

    val tempFileName = formatter.fileName(detailsTuple,gui.fNameColumn)
    val finalFileName = valMed.fileNameIfDuplicate(tempFileName, ".docx")

    gui.message(s"Saving $finalFileName ...")
    
    generator.generate(detailsAsJMap,finalFileName)(saver)
  }
}

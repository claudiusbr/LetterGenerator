package lettergenerator
package mediator

import formatter.DocxMakerFormatter
import generators.DocxGenerator

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import java.util.{HashMap => JHashMap}

class DocxMediator(gui: renderer.Wizard, docPack: WordprocessingMLPackage) {
  val formatter = new DocxMakerFormatter
  val generator = new DocxGenerator(docPack)
  
  def makeManyDocx(details: Details, valMed: ValidationMediator)(
    saver: SaveToZipFile = new SaveToZipFile(docPack)): Unit = {

    details.tuples.foreach(makeSingleDocx(_,valMed)(saver))
    gui.message("Done!")
  }
  
  private def makeSingleDocx(detailsTuple: Map[String,String], 
    valMed: ValidationMediator)(saver: SaveToZipFile): Unit = {

    val detailsAsJMap = formatter.prepareMap(
        detailsTuple,gui.fnAlsoInTemplate,gui.fNameColumn)

    val tempFileName = formatter.fileName(detailsTuple,gui.fNameColumn)
    val finalFileName = valMed.fileNameIfDuplicate(tempFileName, ".docx")

    gui.message(s"Saving $finalFileName ...")
    
    generator.generate(detailsAsJMap,finalFileName)(saver)
  }
}

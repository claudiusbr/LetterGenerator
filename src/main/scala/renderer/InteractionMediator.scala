package renderer

import formatter._

import org.docx4j.XmlUtils
import org.docx4j.wml.Document
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart

import scala.swing.MainFrame
import formatter.PathValidator

import java.util.{HashMap => JHashMap}

case class InteractionMediator() {
  def runInterface(ui: MainFrame): Unit = {
    ui.visible = true
  }
  
  def submit(ui: MainFrame): Unit = {
    val gui = ui.asInstanceOf[Wizard]
    gui.message.text = "Processing..."
    validatePaths(gui) 
  }
  
  def validatePaths(gui: Wizard): Unit = {
    val validator = PathValidator() 

    if (validator.validate(gui.detailsFile)) {
      if (validator.validate(gui.templateFile)) {
        if(validator.validate(gui.destinationFolder)) {
          loadInformation(gui)
        } else {
          gui.message.text = 
            "Could not reach the destination folder."+
            " Please check if it is correct, or report this issue."
        }
      } else {
        gui.message.text = 
          "Could not reach the template file."+
          " Please check if it is correct, or report this issue."
      }
    } else {
      gui.message.text = 
        "Could not reach the details file." +
        " Please check if it is correct, or report this issue."
    }
  }
  
  def loadInformation(gui: Wizard): Unit = {
    val details: Seq[JHashMap[String,String]] = 
      new DetailsFormatter(CsvInput(gui.detailsFile)).details

    generate(details,gui)
  }
  
  def generate(details: Seq[JHashMap[String,String]], gui: Wizard): Unit = {

    val docPack = TemplateFormatter(DocxInput(gui.templateFile)).template
        
    val template: MainDocumentPart = docPack.getMainDocumentPart

    val destination = gui.destinationFolder
    
    var counter = 0

    for(map <- details) {

      val jaxbElement = template.getJaxbElement

      println(jaxbElement.toString())
      
      val xml: String = XmlUtils.marshaltoString(jaxbElement, true)
      
      val replaced: Object = XmlUtils.unmarshallFromTemplate(xml, map)
      
      template.setJaxbElement(replaced.asInstanceOf[Document])
      
      counter += 1

      new SaveToZipFile(docPack).save(s"$destination/Output$counter.docx")
      
      template.setJaxbElement(jaxbElement)
    }
    gui.message.text = "Done!"
  }
}
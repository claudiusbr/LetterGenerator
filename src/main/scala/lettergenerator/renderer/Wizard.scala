package lettergenerator
package renderer

import scala.swing.{MainFrame, Label, CheckBox, TextField}
import scala.swing.{Dialog, ComboBox, FileChooser}

import scala.swing.event.ValueChanged

import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * the main frame. Responsible for laying out the elements
 * @param medium an Interactor object
 */
class Wizard(medium: Interactor) extends MainFrame {
  
  private val TextWidth: Int = 56

  // to make the buttons, labels and textfields
  private[renderer] val elementMaker = new ElementMaker()
  
  // to arrange the interface's elements
  val elementOrganiser = new ElementOrganiser(this)
  
  // for opening files and directories
  private val csvOpener, docxOpener, dirOpener = elementMaker.makeFileChooser()

  // source of letter header details
  private[renderer] val (detailsLabel, detailsText, detailsButton) = 
    elementMaker.makeOpenFileElements("Please choose the details file with the"
      + " column names to create the letters", csvOpener, TextWidth)

  // source of letter template
  private[renderer] val (templateLabel, templateText, templateButton) = 
    elementMaker.makeOpenFileElements("Please choose the file with the "
      + " letter template", docxOpener, TextWidth)

  // destination folder
  private[renderer] val (destinationLabel, destinationText, destinationButton) = 
    elementMaker.makeOpenFileElements("Please choose a destination " 
      + "folder for the letters", dirOpener, TextWidth)

  // drop down box for file name column      
  private var textChangeFlag: String = detailsText.text

  private[renderer] val fileNameLabel = elementMaker.makeLabel(" ") 
  private[renderer] val fileNameColumn = elementMaker.makeComboBox()
  
  // check box to allow blank values (empty cells) in details file
  private[renderer] val allowEmptyCels_ : CheckBox = 
    elementMaker.makeCheckBox("Allow empty cells")
  
  // check box to check if file name is also present in template
  // as a variable to be replaced
  private[renderer] val fnAlsoInTemplate_ : CheckBox = 
    elementMaker.makeCheckBox("File name also part of letter")
  
  private[renderer] val msg: Label = elementMaker.makeLabel("Ready")
  
  def setLayout(title: String): Unit = {
    this.title = title
    
    fnAlsoInTemplate_.selected = false
    allowEmptyCels_.selected = true
    
    listenTo(detailsText)

    reactions += { case ValueChanged(detailsText) => comboBoxRoutine() }
    
    setPreferredExtensions()
    
    elementOrganiser.organise()
  }
  
  def message(text: String): Unit = msg.text = text

  def alert(text: String): Unit = Dialog.showMessage(this,text,"Alert")
  
  def allowEmptyCells: Boolean = allowEmptyCels_.selected
  
  def fNameColumn: String = fileNameColumn.selection.item

  def fnAlsoInTemplate: Boolean = fnAlsoInTemplate_.selected

  def submit(): Unit = medium.submit() 
  
  def detailsFile: String = detailsText.text
  def templateFile: String = templateText.text
  def destinationFolder: String = destinationText.text
  
  private def comboBoxRoutine(): Unit = {
    if (detailsFile != textChangeFlag) {

      fileNameColumn.peer.setModel(
          ComboBox.newConstantModel(
              medium.detailsFileHeaders()))
      textChangeFlag = detailsFile
      fileNameColumn.selection.item = ""
      
      if (fileNameColumn.peer.getModel.getSize > 1) 
        fileNameLabel.text = "Please select the column which contains "+
          "the file names for the new documents"
      else fileNameLabel.text = ""
    }
  }
  
  def setPreferredExtensions(): Unit = {
    csvOpener.fileFilter = (new FileNameExtensionFilter("CSV (Comma Separated Values)","csv"))
    docxOpener.fileFilter = (new FileNameExtensionFilter("Word Document","docx"))
    dirOpener.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
  }

}
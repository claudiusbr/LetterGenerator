package lettergenerator
package renderer

import scala.swing._
import scala.swing.event._

import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * the main frame. Responsible for laying out the elements
 * @param medium an InteractionMediator object
 */
class Wizard(medium: InteractionMediator) extends MainFrame {
  title = "Letter Maker Wizard" 
  preferredSize = new Dimension(695,360)
  val TextWidth = 56
  
  // for making the buttons, labels and textfields
  val elementMkr = ElementMaker()
  
  // for opening files and directories
  private val csvOpener = new FileChooser(new File("."))
  csvOpener.fileFilter = (new FileNameExtensionFilter("CSV (Comma Separated Values)","csv"))
  private val docxOpener = new FileChooser(new File("."))
  docxOpener.fileFilter = (new FileNameExtensionFilter("Word Document","docx"))
  private val dirOpener = new FileChooser(new File("."))
  dirOpener.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly

  
  // source of letter header details
  private val (dtLbl, dtTxt, dtBtn) = 
    elementMkr.mkOpenFileElmts("Please choose the file with the"
      + " details which will go on the letters", csvOpener, TextWidth)

  // drop down box for file name column      
  private var textChangeFlag = dtTxt.text
  private val fileNameColumn = new ComboBox(List[String]())
  private val fnLbl = elementMkr.label(" ") 
  def fNameColumn: String = fileNameColumn.selection.item
  
  // check box to check if file name is also present in template
  // as a variable to be replaced
  private val fnAlsoInTemplate_ = new CheckBox("File name also part of letter")
  fnAlsoInTemplate_.selected = false
  def fnAlsoInTemplate: Boolean = fnAlsoInTemplate_.selected
  
  // source of letter template
  private val (tpltLbl, tpltTxt, tpltBtn) = 
    elementMkr.mkOpenFileElmts("Please choose the file with the "
      + " template for the letters", docxOpener, TextWidth)

  // destination folder
  private val (destLbl, destTxt, destBtn) = 
    elementMkr.mkOpenFileElmts("Please choose a destination " 
      + "folder for the letters", dirOpener, TextWidth)
      
  private val msg: Label = elementMkr.label("Ready")
  
  def message(text: String): Unit = msg.text = text

  def alert(text: String): Unit = Dialog.showMessage(this,text,"Alert")
  
  listenTo(dtTxt)

  reactions += { case ValueChanged(dtTxt) => comboBoxRoutine() }
  
  setMaxHeight(dtTxt)
  setMaxHeight(tpltTxt)
  setMaxHeight(destTxt)
  setMaxHeight(fileNameColumn)
  
  val VShortGap: Int = 5
  val VLargeGap: Int = 30
  val HShortGap: Int = 3
  
  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += dtLbl
      contents += Swing.VStrut(VShortGap)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += dtTxt
        contents += Swing.HStrut(HShortGap)
        contents += dtBtn
      }
      contents += Swing.VStrut(VShortGap)
      contents += new BoxPanel(Orientation.Vertical) {
        contents += fnLbl
        contents += Swing.VStrut(VShortGap)
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += fileNameColumn
          contents += Swing.HStrut(HShortGap)
          contents += fnAlsoInTemplate_
        }
      }
    }

    contents += Swing.VStrut(VLargeGap)
    contents += new BoxPanel(Orientation.Vertical) {
     contents += tpltLbl
     contents += Swing.VStrut(VShortGap)
     contents += new BoxPanel(Orientation.Horizontal) {
       contents += tpltTxt
       contents += Swing.HStrut(HShortGap)
       contents += tpltBtn
     }
    }

    contents += Swing.VStrut(VLargeGap)

    contents += new BoxPanel(Orientation.Vertical) {
     contents += destLbl
     contents += Swing.VStrut(VShortGap)
     contents += new BoxPanel(Orientation.Horizontal) {
       contents += destTxt
       contents += Swing.HStrut(HShortGap)
       contents += destBtn
     }
    }
    
    contents += Swing.VStrut(VShortGap)

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += elementMkr.button("Generate Letters", submit())
      contents += Swing.HGlue
    }
    
    contents += Swing.VStrut(3)
    contents += Swing.VStrut(3)
    
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += msg
      contents += Swing.HGlue
    }

    for (e <- contents)
      e.xLayoutAlignment = 0.0
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  
  def setMaxHeight(comp: Component) = 
    comp.maximumSize = new Dimension(Short.MaxValue, comp.preferredSize.height)
  
  def submit(): Unit = medium.submit() 
  
  def detailsFile: String = dtTxt.text
  def templateFile: String = tpltTxt.text
  def destinationFolder: String = destTxt.text
  
  def comboBoxRoutine(): Unit = {
    if (dtTxt.text != textChangeFlag) {
      fileNameColumn.peer.setModel(
          ComboBox.newConstantModel(
              medium.columnsForFileName()))
      textChangeFlag = dtTxt.text
      fileNameColumn.selection.item = ""
      
      if (fileNameColumn.peer.getModel.getSize > 1) 
        fnLbl.text = "Please select the column which contains "+
          "the file names for the new documents"
    }
  }

}











package renderer

import scala.swing._
import java.io.File

/**
 * the main frame. Responsible for laying out the elements
 * @param medium an InteractionMediator object
 */
class Wizard(medium: InteractionMediator) extends MainFrame {
  title = "Letter Maker Wizard" 
  preferredSize = new Dimension(695,320)
  val TextWidth = 56
  
  // for making the buttons and labels
  val elementMkr = ElementMaker()
  
  // for opening files and directories
  private val fOpener = new FileChooser(new File("."))
  private val dOpener = new FileChooser(new File("."))
  dOpener.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly

  
  // source of letter header details
  private val (dtLbl, dtTxt, dtBtn) = 
    elementMkr.mkOpenFileElmts("Please choose the file with the"
      + " details which will go on the letters", fOpener, TextWidth)
  
  // source of letter template
  private val (tpltLbl, tpltTxt, tpltBtn) = 
    elementMkr.mkOpenFileElmts("Please choose the file with the "
      + " template for the letters", fOpener, TextWidth)

  // destination folder
  private val (destLbl, destTxt, destBtn) = 
    elementMkr.mkOpenFileElmts("Please choose a destination " 
      + "folder for the letters", dOpener, TextWidth)
      
  private[renderer] val message: Label = elementMkr.label("Ready")
  
  setMaxHeight(dtTxt)
  setMaxHeight(tpltTxt)
  setMaxHeight(destTxt)
  
  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += dtLbl
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += dtTxt
        contents += Swing.HStrut(3)
        contents += dtBtn
      }
    }

    contents += Swing.VStrut(30)
    contents += new BoxPanel(Orientation.Vertical) {
     contents += tpltLbl
     contents += Swing.VStrut(5)
     contents += new BoxPanel(Orientation.Horizontal) {
       contents += tpltTxt
       contents += Swing.HStrut(3)
       contents += tpltBtn
     }
    }

    contents += Swing.VStrut(30)

    contents += new BoxPanel(Orientation.Vertical) {
     contents += destLbl
     contents += Swing.VStrut(5)
     contents += new BoxPanel(Orientation.Horizontal) {
       contents += destTxt
       contents += Swing.HStrut(3)
       contents += destBtn
     }
    }
    
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += elementMkr.button("Generate Letters", submit(medium))
      contents += Swing.HStrut(6)
      contents += message
      contents += Swing.VGlue
    }

    for (e <- contents)
      e.xLayoutAlignment = 0.0
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  
  /**
   * Inspired by http://otfried.org/scala/gui.html
   */
  def setMaxHeight(s: Component) {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }
  
  def submit(medium: InteractionMediator): Unit = {
    medium.submit(this)
  }
  
  def detailsFile: String = dtTxt.text
  def templateFile: String = tpltTxt.text
  def destinationFolder: String = destTxt.text
}












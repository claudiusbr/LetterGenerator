package renderer

import scala.swing._
import java.io.File

/**
 * Inspired by http://otfried.org/scala/gui.html
 */
class Wizard extends MainFrame {
  title = "Letter Maker Wizard" 
  preferredSize = new Dimension(640,320)
  val msg = new Label("Please choose the csv file with the"
    + " details which will go on the letters")
  
  val path = new TextField {columns = 56}
  
  restrictHeight(path)
  
  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += msg
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += path
        contents+= Swing.HStrut(1)
        contents += new Button("Open") {
          path.text = getFile()
        }
      }
      contents += Swing.VGlue
    }

    for (e <- contents)
      e.xLayoutAlignment = 0.0
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  
  def restrictHeight(s: Component) {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }
  
  def getFile(): String = {
    val opener = new FileChooser(new File("."))
    if (opener.showOpenDialog(null) == FileChooser.Result.Approve)
      opener.selectedFile.getAbsolutePath
    else ""
  }
}












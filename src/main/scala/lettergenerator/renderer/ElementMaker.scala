package lettergenerator
package renderer

import scala.swing._
import java.io.File

/**
 * This class is responsible for making elements to be added
 * to a mainframe
 */
case class ElementMaker() {
  def mkOpenFileElmts(
    label:String, 
    opener: FileChooser,
    textWidth: Int):(Label,TextField,Button) = {

    val tx = textField(textWidth)

    (this.label(label), tx, button("Open",tx.text = getFile(opener)))
  }
  
  def getFile(opener: FileChooser): String = {
    if (opener.showOpenDialog(null) == FileChooser.Result.Approve)
      opener.selectedFile.getAbsolutePath
    else ""
  }
  
  def button(text: String, action: => Unit): Button = Button(text)(action)
  
  def label(text: String): Label = new Label(text)
  
  def textField(width: Int): TextField = new TextField { columns = width }

}

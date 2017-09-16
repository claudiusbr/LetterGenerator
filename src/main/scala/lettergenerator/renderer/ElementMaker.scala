package lettergenerator
package renderer

import scala.swing.{CheckBox,ComboBox,FileChooser,Label,TextField,Button}
import java.io.File

/**
 * This class is responsible for making elements to be added
 * to a mainframe
 */
class ElementMaker() {
  def makeOpenFileElements(
    label:String, 
    opener: FileChooser,
    textWidth: Int):(Label,TextField,Button) = {

    val tx: TextField = makeTextField(textWidth)

    (this.makeLabel(label), tx, makeButton("Open",tx.text = getFile(opener)))
  }
  
  def makeFileChooser(): FileChooser = new FileChooser(new File("."))
  
  def makeComboBox(): ComboBox[String] = new ComboBox(Seq[String]())
  
  def makeCheckBox(msg: String): CheckBox = new CheckBox(msg)
  
  def getFile(opener: FileChooser): String = {
    if (opener.showOpenDialog(null) == FileChooser.Result.Approve)
      opener.selectedFile.getAbsolutePath
    else ""
  }
  
  def makeButton(text: String, action: => Unit): Button = Button(text)(action)
  
  def makeLabel(text: String): Label = new Label(text)
  
  def makeTextField(width: Int): TextField = new TextField { columns = width }
}

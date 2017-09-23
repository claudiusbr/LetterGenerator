package lettergenerator
package renderer

import scala.swing.{BoxPanel,Orientation,Swing}

class ElementOrganiser(gui: Wizard) {

  private val VShortGap: Int = 5
  private val VLargeGap: Int = 30
  private val HShortGap: Int = 3

  def organise(): Unit = {
    gui.contents = new BoxPanel(Orientation.Vertical) {
      contents += new BoxPanel(Orientation.Vertical) {
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += gui.detailsLabel
          contents += Swing.HGlue
        }
        contents += Swing.VStrut(VShortGap)
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += gui.detailsText
          contents += Swing.HStrut(HShortGap)
          contents += gui.detailsButton
        }
        contents += Swing.VStrut(VShortGap)
        contents += new BoxPanel(Orientation.Vertical) {
          contents += new BoxPanel(Orientation.Horizontal) {
              contents += gui.fileNameLabel
              contents += Swing.HGlue
          }
          contents += Swing.VStrut(VShortGap)
          contents += new BoxPanel(Orientation.Horizontal) {
            contents += gui.fileNameColumn
            contents += Swing.HStrut(HShortGap)
            contents += new BoxPanel(Orientation.Vertical) {
              contents += gui.allowEmptyCels_
              contents += Swing.VGlue
              contents += gui.fnAlsoInTemplate_
            }
          }
        }
      }

      contents += Swing.VStrut(VLargeGap)
      contents += new BoxPanel(Orientation.Vertical) {
       contents += new BoxPanel(Orientation.Horizontal) {
         contents += gui.templateLabel
         contents += Swing.HGlue
       }
       contents += Swing.VStrut(VShortGap)
       contents += new BoxPanel(Orientation.Horizontal) {
         contents += gui.templateText
         contents += Swing.HStrut(HShortGap)
         contents += gui.templateButton
       }
      }

      contents += Swing.VStrut(VLargeGap)

      contents += new BoxPanel(Orientation.Vertical) {
       contents += new BoxPanel(Orientation.Horizontal) {
         contents += gui.destinationLabel
         contents += Swing.HGlue
       }
       contents += Swing.VStrut(VShortGap)
       contents += new BoxPanel(Orientation.Horizontal) {
         contents += gui.destinationText
         contents += Swing.HStrut(HShortGap)
         contents += gui.destinationButton
       }
      }
      
      contents += Swing.VStrut(VShortGap)

      contents += new BoxPanel(Orientation.Horizontal) {
        contents += gui.elementMaker.makeButton("Generate Letters", gui.submit())
        contents += Swing.HGlue
      }
      
      contents += Swing.VStrut(VShortGap)
      
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += gui.msg
        contents += Swing.HGlue
      }

      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }
  }
}
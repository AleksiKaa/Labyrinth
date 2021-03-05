package GUI

import scala.swing._
import java.awt.Color


object GUI extends SimpleSwingApplication {

  def top = new MainFrame {
    title    = "Ball"
    contents = new BallPanel
    size     = new Dimension(400, 400)
  }
  class BallPanel extends Panel {

    override def paintComponent(g : Graphics2D) = {
      g.setColor(Color.blue)
      g.fillOval(20, 20, 300, 200)

      g.setColor(Color.black)
      g.drawString("Ceci n'est pas un ovale.", 115,250)
    }
  }


}

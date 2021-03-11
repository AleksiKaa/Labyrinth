import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.scene.control.Label
import scala.collection.mutable.Buffer
import scala.reflect.io.Path

object GameApp extends JFXApp {

    val size = 21
    val root = new GridPane()
    val game = new Game(size)
    val objects = Buffer[Tile]()

    object Win extends Rectangle {
        width = 50
        height = 50
    }

    objects ++= game.initialElements

    stage = new JFXApp.PrimaryStage {
        title.value = s"objects size = ${objects.size}"
        width = (Win.width * game.x).toDouble
        height = (Win.height * (game.y + 1)).toDouble
    }

    val gridPane = new GridPane()

    for (i <-  0 until game.x) { // to be improved
        for(j <- 0 until game.y) {
            game.elementAt(new Position(i, j)) match {
                case Wall => gridPane.add(new Rectangle {       //Wall sprite
                    width  = 50
                    height = 50
                    fill = Black}, i, j)
                case a => if (a.toString.take(4) == "Path" && a.containsPlayer) {   //Path sprite
                    gridPane.add(new Rectangle {
                    width  = 50
                    height = 50
                    fill = White}, i, j)
                } else if (a.toString.take(4) == "Path") {
                    gridPane.add(new Rectangle {                //Bridge sprite
                    width  = 50
                    height = 50
                    fill = Red}, i, j)
                }
                case _ => gridPane.add(Label("wrong!"), i, j)   //test
            }
        }
    }

    val scene = new Scene(root)
    stage.scene = scene

    root.children += gridPane


}
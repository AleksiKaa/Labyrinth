import scalafx.application.{JFXApp, Platform}
import scalafx.scene.shape.Rectangle
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color._
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.scene.input._
import scala.collection.mutable.Buffer
import scalafx.scene.Scene
import Direction._

object GameApp extends JFXApp {
    stage = new JFXApp.PrimaryStage{
     title = "AAA"
     scene = new Scene(1100,1100) {
         val player = Circle(20,20,10,Green)
         val grid = new GridPane()
         val size = 21
         val game = new Game(size)
         val objects = Buffer[Tile]()
         var xCounter = game.player.location.x
         var yCounter = game.player.location.y


         for (i <- 0 until game.x) {
             for (j <- 0 until game.y) {
                 game.elementAt(new Position(i, j)).toString match {
                     case "Wall" => grid.add(new Rectangle {
                         width = 50
                         height = 50
                         fill = Black
                         }, i, j)
                     case "Path" => grid.add(new Rectangle {
                         width = 50
                         height = 50
                         fill = null
                         stroke = Black
                         }, i, j)
                     case "Bridge" => grid.add(new Rectangle {
                         width = 50
                         height = 50
                         fill = Red
                         }, i, j)
                     case _ =>        //???
             }
           }
         }

        grid.add(player, xCounter, yCounter)
        content = grid

        onKeyPressed = (key: KeyEvent) => {
        key.code match {
            case KeyCode.W => if (grid.getChildren.length != objects.length) grid.getChildren.remove(grid.getChildren.last)
                if (yCounter > 0 && game.canMove(Up)) yCounter -= 1
                game.playerMove(Up)
                grid.add(player, xCounter, yCounter)
                content = grid
            case KeyCode.A => if (grid.getChildren.length != objects.length) grid.getChildren.remove(grid.getChildren.last)
                if (xCounter > 0 && game.canMove(Left)) xCounter -= 1
                game.playerMove(Left)
                grid.add(player, xCounter, yCounter)
                content = grid
            case KeyCode.S => if (grid.getChildren.length != objects.length) grid.getChildren.remove(grid.getChildren.last)
                if (yCounter < size - 1 && game.canMove(Down)) yCounter += 1
                game.playerMove(Down)
                grid.add(player, xCounter, yCounter)
                content = grid
            case KeyCode.D => if (grid.getChildren.length != objects.length) grid.getChildren.remove(grid.getChildren.last)
                if(xCounter < size - 1 && game.canMove(Right)) xCounter += 1
                game.playerMove(Right)
                grid.add(player, xCounter, yCounter)
                content = grid
            case KeyCode.Escape => Platform.exit()
            case _ =>
          }
        }
      }
    }
}
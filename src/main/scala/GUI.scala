import scalafx.application.{JFXApp, Platform}
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.scene.input._
import scalafx.scene.Scene
import Direction._
import scalafx.scene.image.{Image, ImageView}
import java.io.FileInputStream

object GameApp extends JFXApp {
    stage = new JFXApp.PrimaryStage{
     title = "AAA"
     scene = new Scene(1100,1100) {

         val grid = new GridPane()
         val size = 21
         val pSize = 50
         val game = new Game(size)
         val mazeCreator = new MazeCreator(game)
         var xCounter = game.player.location.x
         var yCounter = game.player.location.y

         val up     = new ImageView(new Image(new FileInputStream("src\\main\\images\\bearup.png")   , pSize, pSize, false, false))
         val down   = new ImageView(new Image(new FileInputStream("src\\main\\images\\beardown.png") , pSize, pSize, false, false))
         val left   = new ImageView(new Image(new FileInputStream("src\\main\\images\\bearleft.png") , pSize, pSize, false, false))
         val right  = new ImageView(new Image(new FileInputStream("src\\main\\images\\bearright.png"), pSize, pSize, false, false))
         val bridge = new Image(new FileInputStream("src\\main\\images\\bridge.png"), pSize, pSize, false, false)
         var player = down

         //mazeCreator.mazeCreator()

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
                         stroke = null
                         }, i, j)
                     case "Bridge" => grid.add(new ImageView(bridge), i, j)
                     case _ =>        //???
             }
           }
         }

        grid.add(player, xCounter, yCounter)
        content = grid

        onKeyPressed = (key: KeyEvent) => {
        key.code match {

            case KeyCode.W =>
                grid.getChildren.remove(grid.getChildren.last)
                if (yCounter > 0 && game.canMove(Up)) yCounter -= 1
                game.playerMove(Up)
                player = up
                grid.add(player, xCounter, yCounter)
                content = grid

            case KeyCode.A =>
                grid.getChildren.remove(grid.getChildren.last)
                if (xCounter > 0 && game.canMove(Left)) xCounter -= 1
                game.playerMove(Left)
                player = left
                grid.add(player, xCounter, yCounter)
                content = grid

            case KeyCode.S =>
                grid.getChildren.remove(grid.getChildren.last)
                if (yCounter < size - 1 && game.canMove(Down)) yCounter += 1
                game.playerMove(Down)
                player = down
                grid.add(player, xCounter, yCounter)
                content = grid

            case KeyCode.D =>
                grid.getChildren.remove(grid.getChildren.last)
                if(xCounter < size - 1 && game.canMove(Right)) xCounter += 1
                game.playerMove(Right)
                player = right
                grid.add(player, xCounter, yCounter)
                content = grid

            case KeyCode.Escape => Platform.exit()

            case _ =>
          }
        }
      }
    }
}
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.scene.input._
import scalafx.scene.Scene
import Direction._
import scalafx.scene.image.{Image, ImageView}
import java.io.FileInputStream

object GameApp extends JFXApp {
    stage = new JFXApp.PrimaryStage{
     title = "Maze Game"
     scene = new Scene(1100,1100) {

         val grid = new GridPane()
         val size = 52
         val pSize = 20
         val game = new Game(size)
         val mazeCreator = new MazeCreator(game)
         var xCounter = game.player.location.x
         var yCounter = game.player.location.y

         val up     = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowup.png")   , pSize, pSize, false, false))
         val down   = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowdown.png") , pSize, pSize, false, false))
         val left   = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowleft.png") , pSize, pSize, false, false))
         val right  = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowright.png"), pSize, pSize, false, false))
         val bridge = new Image(new FileInputStream("src\\main\\images\\bridge.png"), pSize, pSize, false, false)
         val wall   = new Image(new FileInputStream("src\\main\\images\\wall.png")  , pSize, pSize, false, false)
         val path   = new Image(new FileInputStream("src\\main\\images\\path.png")  , pSize, pSize, false, false)
         val goal   = new Image(new FileInputStream("src\\main\\images\\goal.png")  , pSize, pSize, false, false)
         var player = down

         mazeCreator.mazeCreator()

         for (i <- 0 until game.x) {
             for (j <- 0 until game.y) {
                 game.elementAt(new Position(i, j)).toString match {
                     case "Wall"   => grid.add(new ImageView(wall), i, j)
                     case "Path"   => grid.add(new ImageView(path), i, j)
                     case "Bridge" => grid.add(new ImageView(bridge), i, j)
                     case "Goal"   => grid.add(new ImageView(goal), i, j)
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
                if (game.isComplete) println("jee!")                     //to be modified

            case KeyCode.A =>
                grid.getChildren.remove(grid.getChildren.last)
                if (xCounter > 0 && game.canMove(Left)) xCounter -= 1
                game.playerMove(Left)
                player = left
                grid.add(player, xCounter, yCounter)
                content = grid
                if (game.isComplete) println("jee!")

            case KeyCode.S =>
                grid.getChildren.remove(grid.getChildren.last)
                if (yCounter < size - 1 && game.canMove(Down)) yCounter += 1
                game.playerMove(Down)
                player = down
                grid.add(player, xCounter, yCounter)
                content = grid
                if (game.isComplete) println("jee!")

            case KeyCode.D =>
                grid.getChildren.remove(grid.getChildren.last)
                if(xCounter < size - 1 && game.canMove(Right)) xCounter += 1
                game.playerMove(Right)
                player = right
                grid.add(player, xCounter, yCounter)
                content = grid
                if (game.isComplete) println("jee!")

            case KeyCode.Escape => Platform.exit()

            case _ =>
          }
        }
      }
    }
}
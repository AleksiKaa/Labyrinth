import scalafx.application.{JFXApp, Platform}
import scalafx.scene.layout._
import scalafx.Includes._
import scalafx.scene.input._
import scalafx.scene.Scene
import scalafx.event.ActionEvent
import scalafx.scene.control.TextField
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.text.Text
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.geometry.Pos.Center

import java.io.FileInputStream

import Util._
import Maze.Direction._
import Maze._

object GameApp extends JFXApp {

  stage = new PrimaryStage {
    title = "Maze Game"
    scene = new Scene(300, 230) {

      //start screen

      content = new VBox(20) {
        fill = new LinearGradient(endX = 0, stops = Stops(White, Grey))
        padding = Insets(50, 50, 50, 50)
        alignment = Center

        val text = new Text {
          text = "Enter game size"
          style = "-fx-font: normal bold 20pt sans-serif"
        }
        val textField = new TextField()
        textField.setMaxWidth(200)
        val button = new Button("Enter")
        button.setMinWidth(200)

        var buttonPressed = false

        onKeyPressed = (key: KeyEvent) => key.code match {
          case KeyCode.Escape => Platform.exit()
          case _ =>
        }

        button.setOnAction((t: ActionEvent) => {

          def notValid(s: String) = s.toIntOption.isEmpty || s.toIntOption.get > 100 || s.toIntOption.get < 10

          val textBox = textField.text.value

          if (notValid(textBox) && !buttonPressed) {
            buttonPressed = true
            val text1 = new Text() {
              text = "Please enter a number from 10 to 100."
              fill = DarkRed
            }
            text1.relocate(250, 40)
            children.add(text1)
          } else if (notValid(textBox) && buttonPressed) { // do nothing
          } else {

            //The game itself

            val size = textField.text.value.toInt         // game size in squares
            val pSize = if (size * 20 < 1080) 20 else 10  // square size in pixels

            stage = new PrimaryStage {
              title = "Maze Game"
              scene = new Scene(size * pSize, size * pSize) {

                val grid = new GridPane()
                val game = new Game(size)
                val mazeCreator = new MazeCreator(game)
                val mazeSolver = new MazeSolver(game)
                var xC = game.player.location.x
                var yC = game.player.location.y

                val up     = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowup.png"), pSize, pSize, false, false))
                val down   = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowdown.png"), pSize, pSize, false, false))
                val left   = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowleft.png"), pSize, pSize, false, false))
                val right  = new ImageView(new Image(new FileInputStream("src\\main\\images\\arrowright.png"), pSize, pSize, false, false))
                val bridge = new Image(new FileInputStream("src\\main\\images\\bridge.png"), pSize, pSize, false, false)
                val wall   = new Image(new FileInputStream("src\\main\\images\\wall.png"), pSize, pSize, false, false)
                val path   = new Image(new FileInputStream("src\\main\\images\\path.png"), pSize, pSize, false, false)
                val goal   = new Image(new FileInputStream("src\\main\\images\\goal.png"), pSize, pSize, false, false)
                val blue   = new Image(new FileInputStream("src\\main\\images\\bluecircle.png"), pSize, pSize, false, false)
                val darkblue = new Image(new FileInputStream("src\\main\\images\\darkblue.png"), pSize, pSize, false, false)

                var player = down
                var solutionLength = 0
                var solutionShown = false

                mazeCreator.mazeCreator()

                for (i <- 0 until game.width) {
                  for (j <- 0 until game.width) {
                    game.content()(i)(j).toString match {
                      case "Wall"   => grid.add(new ImageView(wall), i, j)
                      case "Path"   => grid.add(new ImageView(path), i, j)
                      case "Bridge" => grid.add(new ImageView(bridge), i, j)
                      case "Goal"   => grid.add(new ImageView(goal), i, j)
                      case _             =>
                    }
                  }
                }

                grid.add(player, xC, yC)
                content = grid

                onKeyPressed = (key: KeyEvent) => {

                  if (key.controlDown) {

                    key.code match {

                      case KeyCode.O => mazeSolver.printShortest(game.player.location, mazeCreator.returnGoal)

                      case KeyCode.S => (new FileManager(game)).print()

                      case KeyCode.P => {
                        if (solutionShown) {                    //remove old solution from view
                          val path = grid.children.init.takeRight(solutionLength)
                          path.foreach( grid.children.remove(_) )
                        }
                        solutionShown = true
                        val solution = mazeSolver.solution(game.player.location, mazeCreator.returnGoal)
                        solutionLength = solution.length
                        solution.foreach(pos => {
                          if (game.elementAt(pos).toString == "Bridge") grid.add(new ImageView(darkblue), pos.x, pos.y)
                          else grid.add(new ImageView(blue), pos.x, pos.y)
                        })
                        grid.children.remove(player)            //
                        grid.add(player, xC, yC)                //ensures that player is the last Node in children
                      }

                      case _ =>

                    }

                  } else {

                    key.code match {

                      case KeyCode.W =>
                        grid.getChildren.remove(grid.children.last)
                        if (yC > 0 && game.canMove(Up)) yC -= 1
                        game.playerMove(Up)
                        player = up
                        grid.add(player, xC, yC)
                        content = grid

                      case KeyCode.A =>
                        grid.getChildren.remove(grid.children.last)
                        if (xC > 0 && game.canMove(Left)) xC -= 1
                        game.playerMove(Left)
                        player = left
                        grid.add(player, xC, yC)
                        content = grid

                      case KeyCode.S =>
                        grid.getChildren.remove(grid.children.last)
                        if (yC < size - 1 && game.canMove(Down)) yC += 1
                        game.playerMove(Down)
                        player = down
                        grid.add(player, xC, yC)
                        content = grid

                      case KeyCode.D =>
                        grid.getChildren.remove(grid.children.last)
                        if (xC < size - 1 && game.canMove(Right)) xC += 1
                        game.playerMove(Right)
                        player = right
                        grid.add(player, xC, yC)
                        content = grid

                      case KeyCode.Escape => Platform.exit()

                      case _ =>

                    }

                    //Congratulations screen

                    if (game.isComplete) {

                      stage = new PrimaryStage {
                        title = "Maze Game"
                        scene = new Scene {
                        fill = new LinearGradient(endX = 0, stops = Stops(White, Grey))
                          content = new VBox(10) {
                            padding = Insets(50, 50, 50, 50)
                            alignment = Center
                            children = Seq(
                              new Text {
                                text = "Congratulations!"
                                style = "-fx-font: normal bold 20pt sans-serif"
                              },
                              new Text {
                                text = "You beat the game!"
                                style = "-fx-font: normal bold 10pt sans-serif"
                              },
                              new Text {
                                text = "Press ESC to exit the game."
                              })
                          }
                          onKeyPressed = (key: KeyEvent) => key.code match {
                            case KeyCode.Escape => Platform.exit()
                            case _ =>
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        })

        children.add(text)
        children.add(textField)
        children.add(button)
      }
    }
  }
}
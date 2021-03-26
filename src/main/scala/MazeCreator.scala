import Direction._
import scala.util.Random

class MazeCreator(game: Game) {

  val directions = Vector(Up, Right, Down, Left)

  val random = new Random()

  def mazeCreator() = {

    def maze(pos: Position, n: Int): Unit = {

      if (n < 10) {

        var dir: Direction = NoDir

        directions(random.nextInt(4)) match {
          case Up => dir = Up
          case Right => dir = Right
          case Down => dir = Down
          case Left => dir = Left
        }

        if (game.contains(pos.neighbor(dir))) {

          val tile1 = if (game.elementAt(pos.neighbor(dir)) == Wall) new Path else new Bridge
          val tile2 = if (game.elementAt(pos.neighbor(dir).neighbor(dir)) == Wall) new Path else new Bridge

          game.update(pos.neighbor(dir), tile1)
          game.update(pos.neighbor(dir).neighbor(dir), tile2)
          maze(pos.neighbor(dir).neighbor(dir), n)

        } else maze(game.player.location, n + 1)
      }
    }
    maze(game.player.location, 1)
  }


}

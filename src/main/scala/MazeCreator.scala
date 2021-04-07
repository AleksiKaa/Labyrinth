import Direction._
import scala.util.Random
import scala.collection.mutable.Buffer

class MazeCreator(game: Game) {

  val directions = Vector(Up, Right, Down, Left)

  val random = new Random()

  def mazeCreator() = {

    def carve(pos: Position): Unit = {

      var dir = random.nextInt().abs % 4
      var count = 0

      while (count < 4) {

        val tile1 = pos.neighbor(directions(dir))
        val tile2 = tile1.neighbor(directions(dir))

        if (tile2.x > 0 && tile2.x < game.width && tile2.y > 0 && tile2.y < game.width) {
          if (game.elementAt(tile1) == Wall && game.elementAt(tile2) == Wall) {
            game.update(tile1, new Path)
            game.update(tile2, new Path)
            carve(tile2)
          }
        }
        count += 1
        dir = (dir + 1) % 4
      }
    }
    carve(game.player.location)
    game.update(goalPos(), Goal)
  }

  def goalPos(): Position = {                // returns a Position that is on the edge of the Grid and it has atleast one neighbor that is a Path

    var goal: Position = null
    val a = Buffer[Position]()

    def fillPos() = {
      for (i <- 0 until game.width) {
        for (j <- 0 until game.width) {
          a += new Position(i, j)
        }
      }
    }

    def randomPos(): Unit = {

      def rando(n: Int): Unit = {

        val candidate = a(random.nextInt(n))

        var dirToCenter: Direction = NoDir

        candidate.x match {
          case 0 => dirToCenter = Right
          case game.width => dirToCenter = Left
          case _ =>
        }
        candidate.y match {
          case 0 => dirToCenter = Down
          case game.width => dirToCenter = Up
          case _ =>
        }
        if (dirToCenter != NoDir && game.elementAt(candidate.neighbor(dirToCenter)).toString == "Path") {
          goal = candidate
        } else {
          a -= candidate
          rando(n - 1)
        }
      }
      fillPos()
      rando(game.width * game.width + 1)
    }
    randomPos()
    goal
  }
}

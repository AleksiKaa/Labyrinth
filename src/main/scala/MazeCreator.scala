import Direction._

import java.util.NoSuchElementException
import scala.util.Random

class MazeCreator(game: Game) {

  private var goal: Position = new Position(-1, -1)

  private val directions = Vector(Up, Right, Down, Left)

  private val random = new Random(System.nanoTime())

  def doWithProb(prob: Double)(thenAction: => Unit): Unit = {
    require(0.0 <= prob && prob <= 100.0)
    if ((random.nextInt() % 100).abs < prob) thenAction
  }

  def mazeCreator() = {

    def carve(pos: Position): Unit = {

      var dir = random.nextInt().abs % 4
      var count = 0

      while (count < 4) {

        val tile1 = pos.neighbor(directions(dir))
        val tile2 = tile1.neighbor(directions(dir))
        val tile3 = tile2.neighbor(directions(dir))
        val tile4 = tile3.neighbor(directions(dir))

        if (tile4.x > 0 && tile4.x < game.width - 1 && tile4.y > 0 && tile4.y < game.width - 1) {
          if (game.elementAt(tile1) == Wall && game.elementAt(tile2).toString == "Path" && game.elementAt(tile3) == Wall && game.elementAt(tile4) == Wall) {
            game.update(tile1, new Path)
            //doWithProb(50)(game.update(tile2, new Bridge))
            game.update(tile2, new Bridge)
            game.update(tile3, new Path)
            game.update(tile4, new Path)
            carve(tile4)
          }
          else if (game.elementAt(tile2).toString == "Bridge") {
            game.update(tile1, new Path)
            game.update(tile3, new Path)
            game.update(tile4, new Path)
          }
        }
        if (tile2.x > 0 && tile2.x < game.width - 1 && tile2.y > 0 && tile2.y < game.width - 1) {
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

    try {
      carve(game.player.location)
      game.update(goalPos(), Goal)
    } catch {
      case e: NoSuchElementException => {
        this.game.content().foreach(row => row.foreach(tile => tile == Wall))
        carve(game.player.location)
        game.update(goalPos(), Goal)
      }
    }
  }

  def goalPos(): Position = { // returns a Position that is on the edge of the Grid and it has atleast one neighbor that is a Path and is connected to the player

    def randompos(): Unit = {

      val randomPos = new Position(random.nextInt(game.width), random.nextInt(game.width))

      val candidate = game.content()(randomPos.x)(randomPos.y)

      def onEdge(pos: Position) = !(pos.x > 1 && pos.x < game.width - 1 && pos.y > 1 && pos.y < game.width - 1)

      def isSolvable = new MazeSolver(this.game).isSolvable(this.game.player.location, randomPos)

      def neighborIsPath(pos: Position) = pos.neighbors().filter(game.contains(_)).map(game.elementAt(_).toString).contains("Path")

      if (candidate == Wall && onEdge(randomPos) && neighborIsPath(randomPos) && isSolvable) goal = randomPos
      else randompos()
    }

    randompos()
    goal
  }

  def returnGoal = goal


}

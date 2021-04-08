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
    carve(game.player.location)
    game.update(goalPos(), Goal)
  }

  def goalPos(): Position = {                // returns a Position that is on the edge of the Grid and it has atleast one neighbor that is a Path

    var goal: Position = null

    def randompos(): Unit = {

      def onEdge(pos: Position) = !(pos.x > 1 && pos.x < game.width - 1 && pos.y > 1 && pos.y < game.width - 1)

      def neighborIsPath(pos: Position) = pos.neighbors().filter(game.contains(_)).map( game.elementAt(_).toString ).contains( "Path" )

      val randomPos = new Position(random.nextInt(game.width), random.nextInt(game.width))

      val candidate = game.content()(randomPos.x)(randomPos.y)

      if (candidate == Wall && onEdge(randomPos) && neighborIsPath(randomPos)) goal = randomPos
      else randompos()
    }
    randompos()
    goal
  }


}

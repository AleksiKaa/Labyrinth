import Direction._
import scala.util.Random

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
  }

}

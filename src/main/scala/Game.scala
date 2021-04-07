import Direction._

class Game(val width: Int) extends Grid[Tile](width, width){

  val pLoc = width - width / 2 - 1
  val player = new Player(new Position(pLoc, pLoc))
  elementAt(player.location).conPlayer = true

  private def initialSquare(x: Int, y: Int) = {
    //used in making a game world where testing of the game elements is possible
    /*if ( x == 5 && y == 5 || x == 10 && y == 10) new Bridge
    else if ((x >= 1 && x < width - 1) && (y >= 1 && y < width - 1)) new Path
    else Wall*/

    Wall

  }

  def initialElements: Seq[Tile] = for (y <- 0 until this.width; x <- 0 until this.width) yield initialSquare(x, y)

  def canMove(dir: Direction) = {
    elementAt(player.location.neighbor(dir)).toString == "Path" || elementAt(player.location.neighbor(dir)).toString == "Goal" && elementAt(player.location).toString != "Bridge" ||
    elementAt(player.location.neighbor(dir)).toString == "Bridge" ||
    elementAt(player.location).toString == "Bridge" && (dir == player.lastDirection || dir == player.lastDirection.! || player.lastDirection == NoDir)
  }

  def playerMove(dir: Direction) = {
    if (canMove(dir)) {
    this.elementAt(player.location).conPlayer = false
    this.player.move(dir)
    this.elementAt(player.location).conPlayer = true
    }
  }
}

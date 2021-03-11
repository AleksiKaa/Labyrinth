

class Game(width: Int) extends Grid[Tile](width, width){

  val pLoc = width - width / 2
  val player = new Player(new Position(pLoc, pLoc))
  elementAt(player.location).conPlayer = true

  private def initialSquare(x: Int, y: Int) = {
    if ((x >= 1 && x < width - 1) && (y >= 1 && y < width - 1)) new Path
    else Wall
  }

  def initialElements: Seq[Tile] = for (y <- 0 until this.width; x <- 0 until this.width) yield initialSquare(x, y)

  def update() = {}



}

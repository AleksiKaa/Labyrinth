class Game(width: Int) extends Grid[Tile](width, width){

  val pLoc = width - width / 2 - 1
  val player = new Player(new Position(pLoc, pLoc))
  elementAt(player.location).conPlayer = true

  private def initialSquare(x: Int, y: Int) = {
    if ((x >= 1 && x < width - 1) && (y >= 1 && y < width - 1)) new Path
    else Wall
  }

  def canMove(dir: Direction) = {
    elementAt(player.location.neighbor(dir)).toString == "Path" ||
    elementAt(player.location.neighbor(dir)).toString == "Bridge" && dir == player.lastDirection
  }

  def playerMove(dir: Direction) = {
    if (canMove(dir)) {
    this.elementAt(player.location).conPlayer = false
    this.player.move(dir)
    this.elementAt(player.location).conPlayer = true
    }
  }


  def initialElements: Seq[Tile] = for (y <- 0 until this.width; x <- 0 until this.width) yield initialSquare(x, y)

  def update(dir: Direction) = {
    require(!this.elementAt(player.location.neighbor(dir)).isUnpassable)
    playerMove(dir)
  }



}

import Direction._

class Game(val width: Int) extends Grid[Tile](width, width){

  val pLoc = width - width / 2 - 1
  val player = new Player(new Position(pLoc, pLoc))
  elementAt(player.location).conPlayer = true

  def initialElements: Seq[Tile] = Seq.fill(this.size){Wall}

  def canMove(dir: Direction) = {
    (elementAt(player.location.neighbor(dir)).toString == "Path" || elementAt(player.location.neighbor(dir)).toString == "Goal") && elementAt(player.location).toString != "Bridge" ||
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

  def isComplete = if (elementAt(this.player.location) == Goal) true else false
}

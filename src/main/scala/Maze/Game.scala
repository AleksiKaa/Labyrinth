package Maze

import Maze.Direction._

class Game(val width: Int) extends Grid[Tile](width, width){

  val pLoc = width - width / 2 - 1
  val player = new Player(new Position(pLoc, pLoc))

  def initialElements: Seq[Tile] = Seq.fill(this.size){Wall}

  def canMove(dir: Direction) = {
    (elementAt(player.location.neighbor(dir)).toString == "Path" || elementAt(player.location.neighbor(dir)).toString == "Goal") && elementAt(player.location).toString != "Bridge" ||
     elementAt(player.location.neighbor(dir)).toString == "Bridge" ||
     elementAt(player.location).toString == "Bridge" && (dir == player.lastDirection || dir == player.lastDirection.! || player.lastDirection == NoDir)
  }

  def playerMove(dir: Direction) = {
    if (canMove(dir)) {
    this.player.move(dir)
    }
  }

  def isComplete = elementAt(this.player.location) == Goal
}

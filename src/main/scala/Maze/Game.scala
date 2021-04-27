package Maze

import Maze.Direction._

class Game(val width: Int){

  val size = width * width
  val pLoc = width - width / 2 - 1
  val player = new Player(new Position(pLoc, pLoc))
  private val contents: Array[Array[Tile]] = initialElements.toArray.grouped(this.width).toArray.transpose

  def initialElements: Seq[Tile] = Seq.fill(this.size){Wall}

  def content() = contents

  def elementAt(location: Position) = {
    require(this.contains(location), "location doesn't exist")
    this.contents(location.x)(location.y)
  }

  private def contains(x: Int, y: Int) = (x >= 0) && (x <= this.width - 1) && (y >= 0) && (y <= this.width - 1)

  def contains(location: Position): Boolean = this.contains(location.x, location.y)

  def update(pos: Position, tile: Tile) = {
    require(this.contains(pos), "no such position")
    this.contents(pos.x)(pos.y) = tile
  }

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

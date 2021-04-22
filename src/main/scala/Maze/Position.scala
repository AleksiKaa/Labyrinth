package Maze

import Direction._

class Position(val x: Int, val y: Int) {

  def neighbor(dir: Direction): Position = new Position(this.x + dir.xStep, this.y + dir.yStep)

  def neighbors() = Vector(this.neighbor(Up), this.neighbor(Right), this.neighbor(Down), this.neighbor(Left))

  override def toString = s"($x, $y)"

}

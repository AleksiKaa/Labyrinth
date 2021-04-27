package Maze

import scala.reflect.ClassTag

abstract class Grid[T: ClassTag](val x: Int, val y: Int) {

  val size = x * y

  def initialElements: Seq[T]

  private val contents: Array[Array[T]] = initialElements.toArray.grouped(this.x).toArray.transpose

  def content() = contents

  def elementAt(location: Position) = {
    require(this.contains(location), "location doesn't exist")
    this.contents(location.x)(location.y)
  }

  private def contains(x: Int, y: Int) = (x >= 0) && (x <= this.x - 1) && (y >= 0) && (y <= this.y - 1)

  def contains(location: Position): Boolean = this.contains(location.x, location.y)

  def update(pos: Position, tile: T) = {
    require(this.contains(pos), "no such position")
    this.contents(pos.x)(pos.y) = tile
  }

}
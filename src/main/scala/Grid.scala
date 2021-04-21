import scala.reflect.ClassTag

abstract class Grid[Tile: ClassTag](val x :Int, val y :Int) {

  val size = x * y

  def initialElements : Seq[Tile]

  private val contents: Array[Array[Tile]] = initialElements.toArray.grouped(this.x).toArray.transpose

  def content() = contents

  def elementAt(location: Position) = {
    require(this.contains(location), "location doesn't exist")
    this.contents(location.x)(location.y)
  }

  private def contains(x: Int, y: Int) = (x >= 0) && (x <= this.x - 1) && (y >= 0) && (y <= this.y - 1)

  def contains(location: Position): Boolean = this.contains(location.x, location.y)

  def update(pos: Position, tile: Tile) = {
    require(this.contains(pos), "No such Position")
    this.contents(pos.x)(pos.y) = tile
  }

}
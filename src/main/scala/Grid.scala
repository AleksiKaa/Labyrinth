import scala.reflect.ClassTag

abstract class Grid[Tile: ClassTag](val x :Int, val y :Int) {

  val size = x * y

  def initialElements : Seq[Tile]

  private val contents: Array[Array[Tile]] = {
    val elems = try { this.initialElements } catch { case npe: NullPointerException => throw new RuntimeException("NPE", npe) }
    require(elems.sizeIs == this.size, s"elems.size != this.size")
    elems.toArray.grouped(this.x).toArray.transpose
  }

  def elementAt(location: Position) = {
    require(this.contains(location), s"location doesn't exist")
    this.contents(location.x)(location.y)
  }

  private def contains(x: Int, y: Int) = (x >= 0) && (x <= this.x) && (y >= 0) && (y <= this.y)

  def contains(location: Position): Boolean = this.contains(location.x, location.y)

  }
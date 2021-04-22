package Maze

trait Tile {

  def updatePlayer(): Unit

  def containsPlayer: Boolean

}

object Wall extends Tile {

  def updatePlayer(): Unit = Unit

  def containsPlayer = false

  override def toString = "Maze.Wall"

}

class Path extends Tile {

  private var conPlayer = false

  def updatePlayer(): Unit = conPlayer = !conPlayer

  def containsPlayer = conPlayer

  override def toString = "Maze.Path"

}

class Bridge extends Path {

  override def toString = "Maze.Bridge"

}

object Goal extends Tile {

  private var conPlayer = false

  def updatePlayer(): Unit = conPlayer = !conPlayer

  def containsPlayer = conPlayer

  override def toString = "Maze.Goal"
}

case class BreadCrumb() extends Tile {

  def updatePlayer(): Unit = Unit

  def containsPlayer = false

  override def toString = "Maze.BreadCrumb"

}

object BridgeCrumbX extends BreadCrumb{

  override def toString = "BridgeCrumb"

}

object BridgeCrumbY extends BreadCrumb {

  override def toString = "BridgeCrumb"

}


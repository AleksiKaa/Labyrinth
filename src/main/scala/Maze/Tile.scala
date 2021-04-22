package Maze

trait Tile {

  def updatePlayer(): Unit

  def containsPlayer: Boolean

}

object Wall extends Tile {

  def updatePlayer(): Unit = ()

  def containsPlayer = false

  override def toString = "Wall"

}

class Path extends Tile {

  private var conPlayer = false

  def updatePlayer(): Unit = conPlayer = !conPlayer

  def containsPlayer = conPlayer

  override def toString = "Path"

}

class Bridge extends Path {

  override def toString = "Bridge"

}

object Goal extends Tile {

  private var conPlayer = false

  def updatePlayer(): Unit = conPlayer = !conPlayer

  def containsPlayer = conPlayer

  override def toString = "Goal"
}


object BreadCrumb extends Tile {

  def updatePlayer(): Unit = ()

  def containsPlayer = false

  override def toString = "BreadCrumb"

}

object BridgeCrumbX extends Tile {

  def updatePlayer(): Unit = ()

  def containsPlayer = false

  override def toString = "BridgeCrumb"

}

object BridgeCrumbY extends Tile {

  def updatePlayer(): Unit = ()

  def containsPlayer = false

  override def toString = "BridgeCrumb"

}


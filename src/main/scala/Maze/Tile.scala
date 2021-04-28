package Maze

trait Tile

object Wall extends Tile {

  override def toString = "Wall"

}

object Path extends Tile {

  override def toString = "Path"

}

object Bridge extends Tile {

  override def toString = "Bridge"

}

object Goal extends Tile {

  override def toString = "Goal"

}


object BreadCrumb extends Tile

object BridgeCrumbX extends Tile

object BridgeCrumbY extends Tile

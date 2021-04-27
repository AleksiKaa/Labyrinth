package Maze

trait Tile

object Wall extends Tile {

  override def toString = "Wall"

}

class Path extends Tile {

  override def toString = "Path"

}

class Bridge extends Path {

  override def toString = "Bridge"

}

object Goal extends Tile {

  override def toString = "Goal"

}


object BreadCrumb extends Tile {

  override def toString = "BreadCrumb"

}

object BridgeCrumbX extends Tile {

  override def toString = "BridgeCrumbX"

}

object BridgeCrumbY extends Tile {

  override def toString = "BridgeCrumbY"

}


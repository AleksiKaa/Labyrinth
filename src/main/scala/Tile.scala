trait Tile {

  var conPlayer: Boolean

  def isUnpassable: Boolean

  def containsPlayer: Boolean

}

object Wall extends Tile {

  var conPlayer = false

  def isUnpassable = true

  def containsPlayer = conPlayer

  override def toString = "Wall"

}

class Path extends Tile {

  var conPlayer = false

  def isUnpassable = false

  def containsPlayer = conPlayer

  override def toString = "Path"

}

class Bridge extends Path {

  override def toString = "Bridge"

}

object Goal extends Tile {

  var conPlayer = false

  def isUnpassable = false

  def containsPlayer = conPlayer

  override def toString = "Goal"
}

object BreadCrumb extends Tile {

  var conPlayer = false

  def containsPlayer = conPlayer

  def isUnpassable = false

  override def toString = "BreadCrumb"

}

object BridgeCrumbX extends Tile {

  var conPlayer = false

  def containsPlayer = conPlayer

  def isUnpassable = false

  override def toString = "BridgeCrumb"

}

object BridgeCrumbY extends Tile {

  var conPlayer = false

  def containsPlayer = conPlayer

  def isUnpassable = false

  override def toString = "BridgeCrumb"

}


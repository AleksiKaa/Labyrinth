
trait Tile {

  var conPlayer: Boolean

  def isUnpassable: Boolean

  def containsPlayer: Boolean

}

object Wall extends Tile {

  var conPlayer = false

  def isUnpassable = true

  def containsPlayer = conPlayer

}

class Path extends Tile {

  var conPlayer = false

  def isUnpassable = false

  def containsPlayer = conPlayer

}

class Bridge extends Path



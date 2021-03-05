package game

sealed trait Tile {

  def isUnpassable: Boolean

  def containsPlayer: Boolean

}

object Wall extends Tile {

  def isUnpassable = true

  def containsPlayer: Boolean = false

}

class Path extends Tile {

  var conPlayer = false

  def isUnpassable = false

  def containsPlayer = conPlayer

}

class Bridge extends Path



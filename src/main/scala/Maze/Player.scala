package Maze

import Direction._

class Player(init: Position) {

  private var currentLocation = init

  private var lastDir: Direction = NoDir

  def move(dir: Direction) = {
    currentLocation = currentLocation.neighbor(dir)
    lastDir = dir
  }

  def location = currentLocation

  def lastDirection = lastDir

}

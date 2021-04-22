package Util

import Maze.Direction._
import Maze.Game
import Maze._

class MazeSolver(game: Game) {

  def shortestPath(x: Int, y: Int, goalX: Int, goalY: Int, lastDir: Direction): Int = {

    val playerPos = new Position(x, y)

    if (x == goalX && y == goalY) 0
    else if (x < 0 || x >= game.width || y < 0 || y >= game.width || game.elementAt(playerPos) == Wall || game.elementAt(playerPos).toString == "Maze.BreadCrumb"
                   || (game.elementAt(playerPos) == BridgeCrumbX && (lastDir == Right || lastDir == Left)) || (game.elementAt(playerPos) == BridgeCrumbY && (lastDir == Up || lastDir == Down))) 1000000000
    else {

      val currentTile = this.game.elementAt(playerPos)

      if (currentTile.toString == "Maze.Bridge" && (lastDir == Right || lastDir == Left)) {

        this.game.update(playerPos, BridgeCrumbX)
        val shortest = (shortestPath(x + 1, y, goalX, goalY, Right) min
                        shortestPath(x - 1, y, goalX, goalY, Left)) + 1
        this.game.update(playerPos, currentTile)
        shortest

      } else if (currentTile.toString == "Maze.Bridge" && (lastDir == Up || lastDir == Down)) {

        this.game.update(playerPos, BridgeCrumbY)
        val shortest = (shortestPath(x, y + 1, goalX, goalY, Down) min
                        shortestPath(x, y - 1, goalX, goalY, Up)) + 1
        this.game.update(playerPos, currentTile)
        shortest

      } else {

          this.game.update(playerPos, BreadCrumb())
          val shortest = (shortestPath(x + 1, y, goalX, goalY, Right) min
                          shortestPath(x - 1, y, goalX, goalY, Left) min
                          shortestPath(x, y + 1, goalX, goalY, Down) min
                          shortestPath(x, y - 1, goalX, goalY, Up)) + 1
          this.game.update(playerPos, currentTile)
          shortest

      }
    }
  }

  def isSolvable(playerPos: Position, goalPos: Position) = shortestPath(playerPos.x, playerPos.y, goalPos.x, goalPos.y, this.game.player.lastDirection) < 1000000000

  def printShortest(playerPos: Position, goalPos: Position) = println("the shortest path to goal is " + shortestPath(playerPos.x, playerPos.y, goalPos.x, goalPos.y, this.game.player.lastDirection) + " steps")

  def returnShorter[T](a: (Int, Seq[T]), b: (Int, Seq[T])) : (Int, Seq[T]) = if (a._1 < b._1) (a._1 + 1, a._2) else (b._1 + 1, b._2)

  def solvePath(x: Int, y: Int, goalX: Int, goalY: Int, lastDir: Direction, seq: Seq[Position]): (Int, Seq[Position]) = {

    val playerPos = new Position(x, y)

    if (x == goalX && y == goalY) (0, seq)
    else if (x < 0 || x >= game.width || y < 0 || y >= game.width || game.elementAt(playerPos) == Wall || game.elementAt(playerPos).toString == "Maze.BreadCrumb"
                   || (game.elementAt(playerPos) == BridgeCrumbX && (lastDir == Right || lastDir == Left)) || (game.elementAt(playerPos) == BridgeCrumbY && (lastDir == Up || lastDir == Down))) (1000000000, Seq())
    else {

      val currentTile = this.game.elementAt(playerPos)

      if (currentTile.toString == "Maze.Bridge" && (lastDir == Right || lastDir == Left)) {

        this.game.update(playerPos, BridgeCrumbX)
        val shortest = returnShorter(solvePath(x + 1, y, goalX, goalY, Right, seq :+ new Position(x + 1, y)),
                                     solvePath(x - 1, y, goalX, goalY, Left , seq :+ new Position(x - 1, y)))
        this.game.update(playerPos, currentTile)
        shortest

      } else if (currentTile.toString == "Maze.Bridge" && (lastDir == Up || lastDir == Down)) {

        this.game.update(playerPos, BridgeCrumbY)
        val shortest = returnShorter(solvePath(x, y + 1, goalX, goalY, Down, seq :+ new Position(x, y + 1)),
                                     solvePath(x, y - 1, goalX, goalY, Up  , seq :+ new Position(x, y - 1)))
        this.game.update(playerPos, currentTile)
        shortest

      } else {

        this.game.update(playerPos, BreadCrumb())

        val shortest = returnShorter(
                       returnShorter(
                       returnShorter(solvePath(x + 1, y, goalX, goalY, Right, seq :+ new Position(x + 1, y)),
                                     solvePath(x - 1, y, goalX, goalY, Left , seq :+ new Position(x - 1, y))),
                                     solvePath(x, y + 1, goalX, goalY, Down , seq :+ new Position(x, y + 1))),
                                     solvePath(x, y - 1, goalX, goalY, Up   , seq :+ new Position(x, y - 1)))

        this.game.update(playerPos, currentTile)
        shortest
      }
    }
  }

  def solution(playerPos: Position, goalPos: Position): Seq[Position] = solvePath(playerPos.x, playerPos.y, goalPos.x, goalPos.y, game.player.lastDirection, Seq())._2.init

  def printSolveSeq(playerPos: Position, goalPos: Position) = {
    val a = solvePath(playerPos.x, playerPos.y, goalPos.x, goalPos.y, this.game.player.lastDirection, Seq())._2
    println(a + "\n" + a.length + " steps")
  }

}

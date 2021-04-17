import Direction._

class MazeSolver(game: Game) {

  def shortestPath(x: Int, y: Int, goalX: Int, goalY: Int, lastDir: Direction): Int = {

    val playerPos = new Position(x, y)

    if (x == goalX && y == goalY) 0
    else if (x < 0 || x >= game.width || y < 0 || y >= game.width || game.elementAt(playerPos) == Wall || game.elementAt(playerPos) == BreadCrumb
                   || (game.elementAt(playerPos) == BridgeCrumbX && (lastDir == Right || lastDir == Left)) || (game.elementAt(playerPos) == BridgeCrumbY && (lastDir == Up || lastDir == Down))) 1000000000
    else {

      val breadCrumb = this.game.elementAt(playerPos)

      if (breadCrumb.toString == "Bridge" && (lastDir == Right || lastDir == Left)) {

        this.game.update(playerPos, BridgeCrumbX)
        val shortest = (shortestPath(x + 1, y, goalX, goalY, Right) min
                        shortestPath(x - 1, y, goalX, goalY, Left)) + 1
        this.game.update(playerPos, breadCrumb)
        shortest

      } else if (breadCrumb.toString == "Bridge" && (lastDir == Up || lastDir == Down)) {

        this.game.update(playerPos, BridgeCrumbY)
        val shortest = (shortestPath(x, y + 1, goalX, goalY, Down) min
                        shortestPath(x, y - 1, goalX, goalY, Up)) + 1
        this.game.update(playerPos, breadCrumb)
        shortest

      } else {

          this.game.update(playerPos, BreadCrumb)
          val shortest = (shortestPath(x + 1, y, goalX, goalY, Right) min
                          shortestPath(x - 1, y, goalX, goalY, Left) min
                          shortestPath(x, y + 1, goalX, goalY, Down) min
                          shortestPath(x, y - 1, goalX, goalY, Up)) + 1
          this.game.update(playerPos, breadCrumb)
          shortest

      }
    }
  }

  def isSolvable(playerPos: Position, goalPos: Position) = shortestPath(playerPos.x, playerPos.y, goalPos.x, goalPos.y, this.game.player.lastDirection) != 1000000001

  def printShortest(playerPos: Position, goalPos: Position) = println("the shortest path to goal is " + shortestPath(playerPos.x, playerPos.y, goalPos.x, goalPos.y, this.game.player.lastDirection) + " steps")

}

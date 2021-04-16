class MazeSolver(game: Game) {

  def shortestPath(x: Int, y: Int, goalX: Int, goalY: Int): Int = {

    val playerPos = new Position(x, y)

    if (x == goalX && y == goalY) 0
    else if (x < 0 || x >= game.width || y < 0 || y >= game.width || game.elementAt(playerPos) == Wall || game.elementAt(playerPos) == BreadCrumb) 1000000000
    else {

      val breadCrumb = this.game.elementAt(playerPos)
      this.game.update(playerPos, BreadCrumb)
      val shortest = (shortestPath(x + 1, y, goalX, goalY) min
                      shortestPath(x - 1, y, goalX, goalY) min
                      shortestPath(x, y + 1, goalX, goalY) min
                      shortestPath(x, y - 1, goalX, goalY)) + 1
      this.game.update(playerPos, breadCrumb)
      shortest
    }
  }

  def isSolvable(playerPos: Position, goalPosCandidate: Position) = shortestPath(playerPos.x, playerPos.y, goalPosCandidate.x, goalPosCandidate.y) != 1000000000

  def printShortest(playerPos: Position, goalPos: Position) = println(shortestPath(playerPos.x, playerPos.y, goalPos.x, goalPos.y))

}

class Game(width: Int, height: Int) extends Grid[Tile](width, height){

  private def initialSquare(x: Int, y:Int) = {
    if ((x >= 1 && x <= width) && (y >= 1 && y <= height)) new Path
    else Wall
  }

  def initialElements = for (y <- 0 until this.height; x <- 0 until this.width) yield initialSquare(x, y)

}

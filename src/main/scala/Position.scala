class Position(val x: Int, val y: Int) {

  def relative(direction: Direction, distance: Int) =
    new Position(this.x + direction.xStep * distance,
             this.y + direction.yStep * distance)

  def neighbor(direction: Direction): Position = this.relative(direction, 1)

}

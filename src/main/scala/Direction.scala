

sealed abstract class Direction(val xStep: Int, val yStep: Int) {

}

  object Direction {

    case object Up    extends Direction(0, -1)

    case object Right extends Direction(1, 0)

    case object Down  extends Direction(0, 1)

    case object Left  extends Direction(-1, 0)

    case object NoDir extends Direction(0, 0)

    val clockwise = Vector[Direction](Up, Right, Down, Left)
    val count = clockwise.size

    private val next = clockwise.zip(clockwise.tail ++ clockwise.init).toMap
    private val previous = this.next.map( _.swap )


  }
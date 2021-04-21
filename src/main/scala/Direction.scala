sealed abstract class Direction(val xStep: Int, val yStep: Int) {

  def ! :Direction

}

  object Direction {

    case object Up    extends Direction(0, -1) {
      def ! = Down
    }

    case object Right extends Direction(1, 0 ) {
      def ! = Left
    }

    case object Down  extends Direction(0,  1) {
      def ! = Up
    }

    case object Left  extends Direction(-1, 0) {
      def ! = Right
    }

    case object NoDir extends Direction(0,  0) {
      def ! = this
    }
  }
class Player(init: Position) {

  private var currentLocation = init

  def move(dir: Direction) = {
    currentLocation = currentLocation.neighbor(dir)
  }

  def location = currentLocation

}

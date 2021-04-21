import java.io._

class FileManager(game: Game) {

  val data = game.content().transpose.map( _.map( _.toString ) )
  data(game.player.location.y)(game.player.location.x) = "Player" // clean

  val dataToNum: Array[String] = data.map( _.map {
    case "Wall"   => "[]"
    case "Path"   => "  "
    case "Bridge" => "><"
    case "Goal"   => "--"
    case "Player" => "XD" // clean this
  } ).map( a => a.mkString(" ") )

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

  def print() = {
    printToFile(new File("LabyrinthSave.txt")) {
      p => dataToNum.foreach(p.println)
      }
    }

}
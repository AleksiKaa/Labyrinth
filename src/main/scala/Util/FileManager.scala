package Util

import Maze._
import java.io._

class FileManager(game: Game) {

  val data = game.content().transpose.map( _.map( _.toString ) )
  data(game.player.location.y)(game.player.location.x) = "oO"

  val dataInString: Array[String] = data.map( _.map {
    case "Wall"   => "[]"
    case "Path"   => "  "
    case "Bridge" => "><"
    case "Goal"   => "--"
    case "oO"     => "oO"
    case _        =>
  } ).map( a => a.mkString(" ") )

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

  def print() = {
    printToFile(new File("LabyrinthSave.txt")) {
      p => dataInString.foreach(p.println)
      }
    }

}
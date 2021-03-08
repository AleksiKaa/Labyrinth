import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
import scalafx.scene.Node

// Starting point for our ScalaFX application.
object GameApp extends JFXApp {

    // Objects drawn in window are children of this Scene
    val root = new Scene

    stage = new JFXApp.PrimaryStage {
        title.value = "Labyrinth"
        width = 800
        height = 800
        scene = root
    }

    // A rectangle object
    val rectangle = new Rectangle {
        width = 50
        height = 50
        x = 10
        y = 10
        fill = Blue
    }

    // Add rectangle to scene
    root.content = Array[Node](rectangle)

}
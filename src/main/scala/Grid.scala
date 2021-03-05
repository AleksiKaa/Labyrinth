import scala.reflect.ClassTag

abstract class Grid[Element: ClassTag](val x :Int, val y :Int) {

    val size = x * y

  }
package scala.example.implicitly
import scala.language.implicitConversions

/**
 * Pixel转成Dp的实现, 缺省构造函数Any对外不可见
 */
class PixelToDp private (pixel: Any) {

  /**重载构造函数，String和Int参数对外可见**/
  def this(pixel: String) = this(pixel.asInstanceOf[Any]) //转型成Any
  def this(pixel: Int) = this(pixel.asInstanceOf[Any])

  private val dpi = 96
  private val pixelString = """(\d+)px""".r

  def pixelToDp() = {
    pixel match {
      //匹配Int型
      case i: Int              => i / dpi
      //匹配正则表达式，参数 number 对应表达式中的(\\d+)里的值
      case pixelString(number) => number.toInt / dpi
    }
  }
}

/**
 * 	采用隐式转换，动态扩展Int和String的方法，将pixel像素转换成dp
 */
object TypeExtension extends App {

  implicit def pixelToDp(pixel: Int) = new PixelToDp(pixel)
  implicit def pixelToDp(pixel: String) = new PixelToDp(pixel)

  println(768.pixelToDp())
  println("768px".pixelToDp())
}
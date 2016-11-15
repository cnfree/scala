package scala.example.exception

import scala.util.{ Try, Success, Failure }

/**
 *  Try Match的用法示例
 *  @see http://www.jianshu.com/p/0de79845eaa4
 */
object TryMatchUsage {

  def divideBy(x: Int, y: Int): Try[Int] = {
    Try(x / y)
  }
  def main(args: Array[String]): Unit = {
    println(divideBy(1, 1).getOrElse(0)) // 1
    println(divideBy(1, 0).getOrElse(0)) //0
    divideBy(1, 1).foreach(println) // 1
    divideBy(1, 0).foreach(println) // no print

    divideBy(1, 0) match {
      case Success(i) => println(s"Success, value is: $i")
      case Failure(s) => println(s"Failed, message is: $s")
    }
  }

}
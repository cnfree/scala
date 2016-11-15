package scala.example.exception

import java.io.IOException
import java.io.File
import scala.example.io.UsingAnyResource.using
import scala.io.Source
import java.net.URL
/**
 * Either用法示例
 * @see http://udn.yyuap.com/doc/guides-to-scala-book/chp7-the-either-type.html
 */
object EitherUsage {
  sealed trait StringAsIntFailure
  final case object ReadFailure extends StringAsIntFailure
  final case object ParseFailure extends StringAsIntFailure

  /*
   * Left  返回异常
   * Right 正常返回
   */
  def convertStringAsInt[T](arg: T): Either[StringAsIntFailure, Int] = {
    try {
      arg match {
        case t: File => Right(using(Source.fromFile(t)) {
          rs => rs.getLines().mkString("\n").toInt
        })
        case t: String => Right(t.toInt)
        case t: Int    => Right(t)
      }
    } catch {
      case exn: IOException =>
        Left(ReadFailure)
      case exn: NumberFormatException =>
        Left(ParseFailure)
    }
  }

  // Typical use case using `match`:
  def readStringAsInt[T](arg: T) = {
    //Either模式匹配
    convertStringAsInt(arg) match {
      case Right(num)  => println(num)
      case Left(error) => println(error.toString())
    }
  }

  def getContent(url: URL): Either[String, Iterator[String]] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked for the good of the people!")
    else
      Right(using(Source.fromURL(url)) {
        rs => rs.getLines().mkString("\n").split("\n").iterator
      })

  def main(args: Array[String]): Unit = {
    readStringAsInt(new File("C:\\1.txt"))
    readStringAsInt(new File("C:\\unknown.txt"))
    readStringAsInt("Hello 2016")
    readStringAsInt(2016)
    convertStringAsInt(2016).right.map { t => println(t) }

    //Right立场，忽略Left立场
    val content: Either[String, Iterator[String]] =
      getContent(new URL("http://www.baidu.com")).right.map { t => t }

    content match {
      case Left(message) => println(message)
      case Right(webContent) => println(webContent.fold("")((t1, t2) => {
        t1 + "\n" + t2
      }))
    }
  }
}
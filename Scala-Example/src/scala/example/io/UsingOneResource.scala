package scala.example.io

import scala.io.Source
import scala.language.reflectiveCalls

/**
 * 自动回收SourceBuffer, 借贷模式
 */
object UsingOneResource {

  def using[A, B <: { def close(): Unit }](closeable: B)(f: B => A): A =
    {
      try {
        f(closeable)
      } finally {
        closeable.close() //Debug第一步会执行到此处，用于反射close方法
      }
    }

  def main(args: Array[String]) {
    using(Source.fromFile("""C:\1.txt""", "GBK")) {
      rs => println(rs.getLines().mkString("\n"))
    }
  }

}
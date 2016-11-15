package scala.example.io

import scala.util.{ Try, Success, Failure }
import language._
import java.io._
import scala.io.Source

object UsingAnyResource {

  //自定义 refinement type
  type Closeable = { def close(): Unit }

  /**
   * 匿名类提供Product到closeAfter的隐式转换，所有的Tuple的基类均为 Product trait
   */
  implicit def product2CloseAfter[A <: Product](x: A) = new {
    def closeAfter[B](block: A => B): B =
      try {
        block(x);
      } finally {
        for (i <- 0 until x.productArity; elem = x.productElement(i)) {
          //判断是否为Closeable, type Closeable为refinement，scala不支持直接的模式匹配，需要自定义模式匹配, 用抽取器获得closeable
          elem match {
            case isCloseable(closeable) => closeable.close()
            case _                      =>
          }
        }
      }
  }

  /**
   * 自定义 unapply 方法，提供Closeable的模式匹配
   */
  object isCloseable {
    def containsMethod(x: AnyRef, name: String, params: Array[java.lang.Class[_]]): Boolean =
      {
        Try {
          x.getClass.getMethod(name, params: _*)
        } match {
          //try catch 不建议直接返回false，而是抛出异常，使用 scala 的 Try match 结构返回
          case Failure(_) => false
          case Success(_) => true
        }
      }

    def unapply(close: AnyRef): Option[Closeable] =
      {
        if (containsMethod(close, "close", new Array[Class[_]](0))) {
          Some(close.asInstanceOf[Closeable])
        } else None
      }
  }

  //函数转换，转换调用顺序，形成自定义的语法糖
  def using[A <: Product, B](obj: A)(block: A => B): B = obj closeAfter block

  //简单的借贷模式实现，关闭 Closeable 的资源
  def using[A, B <: Closeable](closeable: B)(f: B => A): A =
    {
      try {
        f(closeable)
      } finally {
        closeable.close() //Debug第一步会执行到此处，用于反射close方法
      }
    }

  def main(args: Array[String]): Unit = {

    //实现一个资源的借贷模式
    using(Source.fromFile("C:\\1.txt")) {
      rs => println(rs.getLines().mkString("\n"))
    }

    //实现多个资源的借贷模式
    using(Source.fromFile("C:\\1.txt"), new PrintWriter("C:\\2.txt"), new Object) {
      case (in, out, any) =>
        out.print(in.getLines().mkString("\r\n"))
    }
  }
}
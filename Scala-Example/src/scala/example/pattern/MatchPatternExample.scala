package scala.example.pattern

import scala.util.Try
import scala.util.Success
import scala.util.Failure

/**
 * 模式匹配用法演示
 */
object MatchPatternExample extends App {

  //元组模式匹配
  val (first, second) = (1, 2)
  println(first + "," + second)

  //列表模式匹配
  val head :: tail = List(1, 2, 3)
  println(head + "," + tail.mkString(","))

  //列表模式匹配2
  val firstElem :: secondElem :: tailList = List(1, 2, 3)
  println(firstElem + "," + secondElem + "," + tailList.mkString(","))

  //值匹配
  val t = 0
  t match {
    case 0 => println(0)
    case 1 => println(1)
    case _ => println("other")
  }

  def toYesOrNo(choice: Int): String = choice match {
    case 1 | 2 | 3 => "yes"
    case 0         => "no"
    case _         => "error"
  }

  def parseArgument(arg: String) = arg match {
    case "-h" | "--help"    => "Help"
    case "-v" | "--version" => "Verion"
    case whatever           => whatever
  }

  //将 if(n==0)else 转换为模式匹配
  def fact(n: Int): Int = n match {
    case 0 => 1
    case n => n * fact(n - 1)
  }

  //简化版
  //def fact_simple(n) = (1 to n).foldLeft(1) { _ * _ }

  //守卫
  val result = t match {
    case x if (x == 0) => "0"
    case x if (x == 1) => "1"
    case _             => "other"
  }
  println(result)

  //List通配匹配
  List(0, 1, 2, 4) match {
    case List(0, _, 2, _*) => println("found it")
    case _                 =>
  }

  case class Person(name: String, age: Int, sex: String)
  val zhang = Person("zhang", 18, "male")
  val wang = Person("wang", 16, "femle")

  //类型匹配
  def getObjectType[T](obj: T) = obj match {
    case t: Int    => "Int"
    case t: String => "String"
    case t: Long   => "Long"
    case t: Person => "Person"
  }
  println(getObjectType(zhang))

  //case Class unapply 匹配
  def getPersonInfo(person: Person) = person match {
    case Person(name, age, sex) => (name, age, sex)
    case _                      => Nil
  }
  val zhangInfo = getPersonInfo(zhang)
  println(zhangInfo)

  trait Expr
  case class Var(name: String) extends Expr
  case class Number(num: Double) extends Expr
  case class UnOp(operator: String, arg: Expr) extends Expr
  case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

  //复杂的case Class unapply 匹配
  def simplifyTop(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e))  => e
    case BinOp("+", e, Number(0)) => e
    case BinOp("*", e, Number(1)) => e
    case _                        => expr
  }
  println(simplifyTop(UnOp("-", UnOp("-", Number(5)))))

  //正则表达式模式匹配
  private val pixelString = """(\d+)px""".r

  val pixelString(pixel) = "1440px"
  println(pixel)

  def pixelToDp[T](pixel: T) = {
    pixel match {
      case i: Int              => i / 96
      case pixelString(number) => number.toInt / 96
    }
  }
  println(pixelToDp("1440px"))

  //Option模式匹配
  def show(value: Option[String]) =
    {
      value match {
        case Some(a) => a
        case None    => "No this Skill"
      }
    }
  println(show(Some("study")))

  case class X() extends Expr
  case class Const(value: Int) extends Expr
  case class Add(left: Expr, right: Expr) extends Expr
  case class Mult(left: Expr, right: Expr) extends Expr
  case class Neg(expr: Expr) extends Expr

  def eval(expression: Expr, xValue: Int): Int = expression match {
    case X()               => xValue
    case Const(cst)        => cst
    case Add(left, right)  => eval(left, xValue) + eval(right, xValue)
    case Mult(left, right) => eval(left, xValue) * eval(right, xValue)
    case Neg(expr)         => -eval(expr, xValue)
  }

  val expr = Add(Const(1), Mult(Const(2), Mult(X(), X())))
  println(eval(expr, 5))

  //异常模式匹配
  try {
    Some(Integer.parseInt("Hello"))
  } catch {
    case ex: NumberFormatException => None
  }
  
  //可以系统学习Try,Success,Failure的实现
  Try {
    Some(Integer.parseInt("Hello"))
  } match {
    case Success(value) => None
    case Failure(e)     => println(e.getMessage)
  }
}
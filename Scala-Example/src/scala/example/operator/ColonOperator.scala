package scala.example.operator
import scala.language.implicitConversions
import scala.language.reflectiveCalls

/**
 * Scala允许用符号作为方法名，用起来像操作符，冒号后缀表示该方法的this参数在右侧。
 */
object Json {
  def |:(obj: Map[_, _]) = {
    val conc = obj.map { case (t1, t2) => s"  $t1: $t2" }.mkString(",\n")
    s"{\n$conc\n}"
  }
}

object Println {
  def |:(obj: Any) = println(obj)
}

object ColonOperator {

  //定义隐式转换方法
  implicit def pipify[T](t: T) = new {
    /**
     * 定义管道方法
     */
    def |[R](f: T => R): R = f(t)
  }

  def json(obj: Map[_, _]) = obj |: Json

  def main(args: Array[String]) {
    //原始的自定义管道操作符
    (Map("a" -> 1, "b" -> 2) |: Json) |: Println

    "Hello Pipe" | println

    //使用隐式转换的自定义管道操作符
    Map("a" -> 1, "b" -> 2) | json | println
  }
}
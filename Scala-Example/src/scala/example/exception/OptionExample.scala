package scala.example.exception

/**
 * Option 用法演示
 *
 * fold(ifSome: A => B , ifNone: => B)
 * 等价于  map(ifSome).getOrElse(ifNone)，
 * 但是 fold 两个分支返回的类型是一致的，
 * getOrElse返回的类型是Any，和 map 分支不一致
 *
 * 因此不推荐使用 getOrElse
 */
object OptionExample {

  def handleOptionWithGoOrElse[T](option: Option[T], defaultValue: T) = {
    println("handleOptionWithGoOrElse: " + option.map { v => v }.getOrElse(defaultValue))
  }

  def handleOptionWithFold[T](option: Option[T], defaultValue: T) = {
    println("handleOptionWithFold: " + option.fold(defaultValue)(v => v))
  }

  def main(args: Array[String]): Unit = {
    val v = Some(42).map { x => x }.getOrElse("FortyTwo")
    println("""val v = Some(42).getOrElse("FortyTwo")""")
    println("The type of v is not Int, but Any")

    println

    val x = Some(42).fold(0) { x => x }
    //val x = Some(42).fold("FortyTwo") { x => x }  -- compile error
    println("""val x = Some(42).fold("FortyTwo") { x => x }""")
    println("Compile error, two branch return types are not same")

    println

    handleOptionWithGoOrElse(None, "Hello 2016")
    handleOptionWithGoOrElse(Some(42), "Hello 2016")

    println

    handleOptionWithFold(None, "Hello 2016")
    handleOptionWithFold(Some(42), "Hello 2016")
  }

}
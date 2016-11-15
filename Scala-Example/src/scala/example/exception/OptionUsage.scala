package scala.example.exception

object OptionUsage {
  def stringToInt(str: String): Option[Int] = {
    try {
      Some(str.toInt)
    } catch {
      case exn: NumberFormatException =>
        None
    }
  }

  // Typical use case using `match`:
  def useMatchToConvert(str: String) =
    stringToInt(str) match {
      case Some(num) => println(s"use match to convert: $num")
      case None      => println(s"""use match to convert string "$str" to number failed""")
    }

  // We can also use `option.map()` and `option.getOrElse()`:
  def useOptionMapToConvert(str: String) =
    stringToInt(str) map { num =>
      println(s"use map getOrElse to convert: $num")
    } getOrElse {
      println(s"""use map getOrElse convert string "$str" to number failed""")
    }

  // We can also use `option.fold()`:
  def useOptionFoldToConvert(str: String) =
    stringToInt(str).fold {
      println(s"""use fold convert string "$str" to number failed""")
    } {
      num => println(s"use fold to convert: $num")
    }

  def main(args: Array[String]): Unit = {
    useMatchToConvert("2016")
    useMatchToConvert("Hello 2016")

    println

    useOptionMapToConvert("2016")
    useOptionMapToConvert("Hello 2016")

    println

    useOptionFoldToConvert("2016")
    useOptionFoldToConvert("Hello 2016")
  }
}
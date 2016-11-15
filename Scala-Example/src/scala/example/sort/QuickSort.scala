package scala.example.sort

/**
 * 快速排序
 */
object QuickSort {

  //由于不是尾递归，会造成栈溢出
  def sort[T](list: List[T])(implicit ord: Ordering[T]): List[T] = {
    //模式匹配用法
    list match {
      case Nil => Nil
      case head :: tail => {
        val (left, right) = tail.partition { ord.compare(_, head) < 0 }
        sort(left) ::: head :: sort(right)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val list = List.range(1, 10)
    println(sort(list))
    println(sort(list)(Ordering.Int.reverse))
  }
}
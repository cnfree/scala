package scala.example.io.sort

/**
 * 归并排序
 */
object MergeSort {
  /** 比较次数  */
  var compareCnt = 0;

  def resetCount() = { compareCnt = 0; println }

  //由于不是尾递归，会造成栈溢出
  def sort[T](list: List[T])(implicit ord: Ordering[T]): List[T] = {
    //list超过一个元素才递归进行排序
    if (list.length > 1) {
      //模式匹配用法
      val (left, right) = list.splitAt(list.length / 2)
      merge(sort(left), sort(right))
    } else {
      list
    }
  }

  def merge[T](left: List[T], right: List[T])(implicit ord: Ordering[T]): List[T] = {
    //模式匹配用法
    (left, right) match {
      case (Nil, right) => right
      case (left, Nil)  => left
      case _ =>
        {
          compareCnt += 1
          if (ord.compare(left.head, right.head) > 0) {
            right.head :: merge(left, right.tail)
          } else {
            left.head :: merge(left.tail, right)
          }
        }
    }
  }

  def main(args: Array[String]): Unit = {
    val list = List.range(1, 10)

    println(sort(list))
    println(s"Compare Count = $compareCnt")

    resetCount()

    println(sort(list)(Ordering.Int.reverse))
    println(s"Compare Count = $compareCnt")
  }
}
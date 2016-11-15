package scala.example.sort
import scala.math.Ordering

/**
 * 冒泡排序
 */
object BubbleSort {

  /** 交换次数  */
  var swapCnt = 0;
  /** 比较次数  */
  var compareCnt = 0;

  def resetCount() = { swapCnt = 0; compareCnt = 0; println }

  //由于不是尾递归，会造成栈溢出
  def sort[T](list: List[T])(implicit ord: Ordering[T]): List[T] = {
    list match {
      case List()       => List()
      //将List的第一个元素和剩余元素排序之后形成的新List进行比较，递归后第一次比较的是最后一个元素
      case head :: tail => bubbleSort(head, sort(tail))
    }
  }

  def bubbleSort[T](element: T, list: List[T])(implicit ord: Ordering[T]): List[T] = {
    if (!list.isEmpty)
      compareCnt += 1
    if (!list.isEmpty && ord.compare(element, list.head) > 0) {
      swapCnt += 1;
      //如果满足条件，list.head和element交换，element继续和list.tail做排序，此处因为不是数组，既可理解为冒泡排序，也可理解为插入排序
      list.head :: bubbleSort(element, list.tail)
    } else {
      element :: list
    }
  }

  def main(args: Array[String]): Unit = {
    val list = List.range(1, 10)

    println(sort(list))
    println(s"Compare Count = $compareCnt")
    println(s"Swap Count = $swapCnt")

    resetCount()

    println(sort(list)(Ordering.Int.reverse))
    println(s"Compare Count = $compareCnt")
    println(s"Swap Count = $swapCnt")
  }
}
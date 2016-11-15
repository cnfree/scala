package scala.example.sort
import scala.math.Ordering

/**
 * 插入排序
 */
object InsertSort {

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
      case head :: tail => insertSort(head, sort(tail), enableSwapCutInc = true)
    }
  }

  //只有从sort方法入口进入的insertSort，swapCnt满足比较条件才会+1
  def insertSort[T](head: T, tail: List[T], enableSwapCutInc: Boolean)(implicit ord: Ordering[T]): List[T] = {
    if (!tail.isEmpty)
      compareCnt += 1
    if (!tail.isEmpty && ord.compare(head, tail.head) > 0) {
      if (enableSwapCutInc)
        swapCnt += 1
      tail.head :: insertSort(head, tail.tail, enableSwapCutInc = false)
    } else {
      //如果不满足比较条件，element插入到list.tail中，然后和list前面的元素合并，此处因为不是数组，既可理解为插入排序，也可理解为冒泡排序
      head :: tail
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
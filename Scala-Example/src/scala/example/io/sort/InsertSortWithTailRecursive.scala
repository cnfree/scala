package scala.example.io.sort

import scala.annotation.tailrec
import java.util.Arrays
import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting

/**
 * 插入排序的尾递归实现，采用了累加器记录递归后产生的数据。
 */
object InsertSortWithTailRecursive {

  @tailrec
  def insertSort[T](x: T, sorted_xs: List[T], acc: List[T] = Nil)(implicit ord: Ordering[T]): List[T] =
    {
      if (sorted_xs.nonEmpty) {
        val y :: ys = sorted_xs
        if (ord.compare(x, y) <= 0) {
          acc ::: x :: y :: ys
        } else {
          insertSort(x, ys, acc :+ y)
        }
      } else {
        acc ::: List(x)
      }
    }

  @tailrec
  def sort[T](xs: List[T], acc: List[T] = Nil)(implicit ord: Ordering[T]): List[T] =
    {
      if (xs.nonEmpty) {
        val x :: rest = xs
        sort(rest, insertSort(x, acc))
      } else {
        acc
      }
    }

  def main(args: Array[String]): Unit = {
    val list = List.range(1, 10)
    println(sort(list))
    println(sort(list)(Ordering.Int.reverse))
  }
}
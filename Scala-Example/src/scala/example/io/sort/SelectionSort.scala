package scala.example.io.sort

import scala.Ordering
import scala.annotation.tailrec

/**
 * 选择排序，尾递归实现，采用了累加器记录递归后产生的数据。
 */
object SelectionSort {

  def maximum[T](xs: List[T])(implicit ord: Ordering[T]): List[T] =
    (List(xs.head) /: xs.tail) {
      (ys, x) =>
        if (ord.compare(x, ys.head) > 0) (x :: ys)
        else (ys.head :: x :: ys.tail)
    }

  @tailrec
  def selectionSort[T](xs: List[T], accumulator: List[T])(implicit ord: Ordering[T]): List[T] = {
    if (xs.isEmpty) accumulator
    else {
      val ys = maximum(xs)
      selectionSort(ys.tail, ys.head :: accumulator)
    }
  }

  def sort[T](xs: List[T])(implicit ord: Ordering[T]) = {
    selectionSort(xs, Nil)
  }

  def main(args: Array[String]): Unit = {
    val list = List.range(1, 10)
    println(sort(list))
    println(sort(list)(Ordering.Int.reverse))
  }
}
package scala.example.io.sort

import scala.collection.mutable.ArrayBuilder
import scala.util.Random
import scala.util.Sorting
import java.util.Arrays
import scala.math.Ordering

object ArraySort {

  def Desc[T: Ordering] = implicitly[Ordering[T]].reverse

  /**
   * 初始化数据用Scala.Array是最快的
   * 不能直接调用 Array.sorted方法排序，因为scala排序的内部实现是由SeqLike实现的，会先将 Array转成了ArraySeq，这一步过程非常慢
   */
  def main(args: Array[String]): Unit = {
    val builder = ArrayBuilder.make[Int]()
    val random = new Random()
    val length = 100000000
    for (i <- 1 to length) {
      builder += random.nextInt(length)
    }

    val array = builder.result

    println(array.take(10).mkString(","))
    val time = System.nanoTime();
    Arrays.parallelSort(array)
    val useTime = (System.nanoTime() - time) / 1000 / 1000 / 1000f;
    println(array.take(10).mkString(","))
    println(array.takeRight(10).reverse.mkString(","))
    println(s"time=$useTime")
  }

}
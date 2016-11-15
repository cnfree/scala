package scala.example.exception

import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 * 简单的 Try Catch 示例
 * @see https://gist.github.com/davegurnell/8e7a4b671f21a7636152
 */
object TryCatchUsage {

  case class Customer(age: Int)
  class Cigarettes
  case class UnderAgeException(message: String) extends Exception(message)
  def buyCigarettes(customer: Customer): Cigarettes =
    if (customer.age < 16)
      throw UnderAgeException(s"Customer must be older than 16 but was ${customer.age}")
    else new Cigarettes

  def main(args: Array[String]): Unit = {
    val youngCustomer = Customer(15)
    try {
      buyCigarettes(youngCustomer)
      println("Yo, here are your cancer sticks! Happy smokin'!")
    } catch {
      case UnderAgeException(msg) => println(msg)
    }

    try {
      val url = new URL("hdfs://www.google.com")
    } catch {
      case e: MalformedURLException => println("bad url " + e)
      case e: IOException           => println("other IO problem " + e)
      case _: Throwable             => println("anything else!")
    } finally {
      // cleanup
    }
  }
}
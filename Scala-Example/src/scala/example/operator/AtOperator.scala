package scala.example.operator

/**
 * '@'字符在模式匹配中用来获取被匹配的对象
 */
object AtOperator extends App {
  val d @ (c @ Some(a), Some(b)) = (Some(1), Some(2))
  println(s"a=$a") //a=1
  println(s"b=$b") //b=2
  println(s"c=$c") //c=Some(1)
  println(s"d=$d") //d=(Some(1),Some(2))

  println

  (Some(1), Some(2)) match {
    case h @ (g @ Some(e), Some(f)) => {
      println(s"e=$e") //e=1
      println(s"f=$f") //f=2
      println(s"g=$g") //g=Some(1)
      println(s"h=$h") //h=(Some(1),Some(2))
    }
  }

  println

  for (y @ Some(x) <- Seq(None, Some(1))) {
    println(s"x=$x") //x=1
    println(s"y=$y") //y=Some(1)
  }

  println

  val List(z, zs @ _*) = List(1, 2, 3, 4)
  println(s"z=$z")   //z=1
  println(s"zs=$zs") //zs=List(2, 3, 4)
}
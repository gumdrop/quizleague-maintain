package quizleague.util
import scala.language.implicitConversions

object StringUtils {
     implicit class StringImprovements(val s: String) {
         import scala.util.control.Exception._
         def toIntOpt = catching(classOf[NumberFormatException]) opt s.toInt
         def toLongOpt = catching(classOf[NumberFormatException]) opt s.toLong
     }
     def toOrdinal(n:Int)=n+{if(n%100/10==1)"th"else(("thstndrd"+"th"*6).sliding(2,2).toSeq(n%10))}
}


package quizleague.rest.calendar

import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp._

object temp {
  val format = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssXXX")
  val date = LocalDateTime.now.format(format)
  
  println(date)
}
package org.chilternquizleague.util

import java.time._
import scalajs.js
import java.time.temporal.TemporalUnit
import java.time.temporal.ChronoUnit

object DateTimeConverters{
  import scala.language.implicitConversions
  
  implicit def yearToInt(year:Year):Int = year.toString.toInt
  implicit def intToYear(int:Int):Year = Year parse int.toString
  implicit def dateToLocalDate(date:js.Date):LocalDate = LocalDate parse s"${date.getFullYear}-${date.getMonth + 1}-${date.getDate}"
  implicit def localDateToDate(date:LocalDate):js.Date = new js.Date(js.Date parse date.toString)
  implicit def dateToLocalTime(date:js.Date):LocalTime = LocalTime parse s"${date.getHours}:${date.getMinutes}"
  implicit def localTimeToDate(date:LocalTime):js.Date = new js.Date(js.Date parse date.toString)
  implicit def longToDuration(duration:Long):Duration = Duration.ofSeconds(duration * 3600)
  implicit def durationToLong(duration:Duration):Long = duration.get(ChronoUnit.SECONDS) / 3600

}
package org.chilternquizleague.util

import java.time._
import scalajs.js
import java.time.temporal.TemporalUnit
import java.time.temporal.ChronoUnit

object DateTimeConverters extends Logging{
  import scala.language.implicitConversions
  
  implicit def yearToInt(year:Year):Int = year.toString.toInt
  implicit def intToYear(int:Int):Year = Year parse int.toString
  implicit def dateToLocalDate(date:js.Date):LocalDate = LocalDate parse s"${date.getFullYear}-${date.getMonth + 1}-${date.getDate}"
  implicit def localDateToDate(date:LocalDate):js.Date = new js.Date(js.Date parse date.toString)
  implicit def dateToLocalTime(date:String):LocalTime = LocalTime parse date
  implicit def localTimeToDate(date:LocalTime):String = s"${date.getHour}:${date.getMinute}"
  implicit def intToDuration(duration:Float):Duration = Duration.ofSeconds((3600 * duration).toLong)
  implicit def durationToInt(duration:Duration):Float = duration.getSeconds.toFloat / 3600

}
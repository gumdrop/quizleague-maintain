package org.chilternquizleague.util

import java.time.Year
import java.util.Date

object DateTimeConverters{
  import scala.language.implicitConversions
  
  implicit def yearToString(year:Year):String = year.toString
  implicit def stringToYear(string:String):Year = if(string == null) null else Year parse string

 
}
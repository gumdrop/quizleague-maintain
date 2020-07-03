package quizleague.domain

import java.time.{Duration, LocalDate, LocalDateTime, LocalTime}


case class Fixtures(
    id:String, 
    description:String,
    date:LocalDate,
    start:LocalTime,
    retired:Boolean = false) extends Entity
    
case class Fixture(
  id:String,
  description:String,
  venue: Option[Ref[Venue]],
  home: Ref[Team],
  away:Ref[Team],
  result : Option[Result],
  retired:Boolean = false
) extends  Entity

case class Result(
    homeScore:Int,
    awayScore:Int,
    submitter:Option[Ref[User]],
    note:Option[String]
   )


case class Report(
    team:Ref[Team],
    text:Ref[Text],
    ) extends Entity{
   val id = ""
   val retired = false
}


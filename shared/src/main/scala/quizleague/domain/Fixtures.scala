package quizleague.domain

import java.time.LocalTime
import java.time.Duration
import java.time.LocalDate


case class Fixtures(
    id:String, 
    description:String,
    parentDescription:String,
    start:LocalTime,
    duration:Duration,
    fixtures:List[Fixture],
    retired:Boolean = false) extends Entity
    
case class Fixture(
  id:String, 
  venue: Ref[Venue],
  date: LocalDate,
  time: LocalTime,
  duration : Duration,
  retired:Boolean = false
) extends BaseEvent with Entity
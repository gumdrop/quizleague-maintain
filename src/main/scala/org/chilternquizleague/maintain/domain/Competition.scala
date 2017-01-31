package org.chilternquizleague.maintain.domain

import java.util.Date
import java.time.LocalDate
import java.time.LocalTime
import java.time.Duration
import scala.collection.SortedSet


sealed trait Competition extends Entity{
  
  val name:String
  override val retired = false
  
}

sealed trait SingletonCompetition extends Competition{
    
  val date:LocalDate
  val startTime:LocalTime
  val duration:Duration
}

sealed trait ScheduledCompetition extends Competition{

  val startTime:LocalTime
  val duration:Duration
}

sealed trait ResultsCompetition extends Competition{
   val results:SortedSet[Results]
}

sealed trait FixturesCompetition extends Competition{
  this:ResultsCompetition =>
  val fixtures:SortedSet[Fixtures]
}

sealed trait TeamCompetition extends FixturesCompetition with ResultsCompetition

sealed trait BaseLeagueCompetition extends TeamCompetition with ScheduledCompetition

sealed trait MainLeagueCompetition extends BaseLeagueCompetition{
  val subsidiary:SubsidiaryCompetition with ResultsCompetition
}

sealed trait CupCompetition extends TeamCompetition with ScheduledCompetition

sealed trait SubsidiaryCompetition 

case class LeagueCompetition(
  id:String,
  name:String,
  startTime:LocalTime,
  duration:Duration,
  fixtures:SortedSet[Fixtures],
  results:SortedSet[Results],
  subsidiary:SubsidiaryCompetition with ResultsCompetition
  
) extends MainLeagueCompetition




package org.chilternquizleague.maintain.domain

import java.util.Date
import java.time.LocalDate
import java.time.LocalTime
import java.time.Duration



sealed trait Competition extends Entity{
  
  val name:String
  val text:Ref[Text]
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
   val results:List[Ref[Results]]
}

sealed trait FixturesCompetition extends Competition{
  this:ResultsCompetition =>
  val fixtures:List[Ref[Fixtures]]
}

sealed trait TeamCompetition extends FixturesCompetition with ResultsCompetition

sealed trait CompetitionTables{
    val tables:List[Ref[LeagueTable]]
}

sealed trait BaseLeagueCompetition extends TeamCompetition with ScheduledCompetition with CompetitionTables

sealed trait MainLeagueCompetition extends BaseLeagueCompetition{
  val subsidiary:SubsidiaryCompetition with ResultsCompetition
}

sealed trait KnockoutCompetition extends TeamCompetition with ScheduledCompetition

sealed trait SubsidiaryCompetition 

case class LeagueCompetition(
  id:String,
  name:String,
  startTime:LocalTime,
  duration:Duration,
  fixtures:List[Ref[Fixtures]],
  results:List[Ref[Results]],
  tables:List[Ref[LeagueTable]],
  text:Ref[Text],
  subsidiary:SubsidiaryCompetition with ResultsCompetition
  
) extends MainLeagueCompetition

case class CupCompetition(
  id:String,
  name:String,
  startTime:LocalTime,
  duration:Duration,
  fixtures:List[Ref[Fixtures]],
  results:List[Ref[Results]],
  text:Ref[Text]
) extends KnockoutCompetition

case class SubsidiaryLeagueCompetition(
  id:String,
  name:String,
  results:List[Ref[Results]],
  tables:List[Ref[LeagueTable]],
  text:Ref[Text]
) extends SubsidiaryCompetition with ResultsCompetition with CompetitionTables

case class LeagueTable(id:String,retired:Boolean=false) extends Entity


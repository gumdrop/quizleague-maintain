package org.chilternquizleague.maintain.domain

import java.util.Date
import java.time.LocalDate
import java.time.LocalTime
import java.time.Duration
import io.circe.generic.JsonCodec

import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
import org.chilternquizleague.util.json.codecs.DomainCodecs._


@JsonCodec
sealed trait Competition extends Entity
{

  
  val name:String
  //val text:Ref[Text]
  override val retired = false
  
}

case class LeagueCompetition(
  id:String,
  name:String,
  startTime:LocalTime,
  duration:Duration/*,
  fixtures:List[Ref[Fixtures]],
  results:List[Ref[Results]],
  tables:List[Ref[LeagueTable]],
  text:Ref[Text],
  subsidiary:Competition with SubsidiaryCompetition with ResultsCompetition*/
  
) extends Competition// with MainLeagueCompetition

object Competition

/*
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

*/




 trait SingletonCompetition{
    
  val date:LocalDate
  val startTime:LocalTime
  val duration:Duration
}

 trait ScheduledCompetition{

  val startTime:LocalTime
  val duration:Duration
}

 trait ResultsCompetition{
   val results:List[Ref[Results]]
}

 trait FixturesCompetition{
  this:ResultsCompetition =>
  val fixtures:List[Ref[Fixtures]]
}

 trait TeamCompetition extends FixturesCompetition with ResultsCompetition

 trait CompetitionTables{
    val tables:List[Ref[LeagueTable]]
}

 trait BaseLeagueCompetition extends TeamCompetition with ScheduledCompetition with CompetitionTables

 trait MainLeagueCompetition extends BaseLeagueCompetition{
  val subsidiary:SubsidiaryCompetition with ResultsCompetition
}

 trait KnockoutCompetition extends TeamCompetition with ScheduledCompetition

 trait SubsidiaryCompetition







case class LeagueTable(id:String,retired:Boolean=false) extends Entity




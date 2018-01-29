package quizleague.domain.statistics

import java.util.logging.Logger
import quizleague.domain._
import org.threeten.bp.LocalDate


case class Statistics(
  id:String,
  team:Ref[Team],
  season:Ref[Season],
  seasonStats:SeasonStats,
  weekStats:Map[String,WeekStats],
  retired:Boolean = false

) extends Entity
  

 
//  def addWeekStats(date:Date,pointsFor:Int,pointsAgainst:Int):Unit = {
//    
//   val stats = statsForDate(date)
//   
//    stats.pointsFor  = pointsFor
//    stats.pointsAgainst  = pointsAgainst
//    stats.pointsDifference  = pointsFor - pointsAgainst
//    
//    updateFromCurrent(stats, pointsFor, pointsAgainst)
//    
//  }
//  
//  private def updateFromCurrent(stats:WeekStats, pointsFor:Int = 0, pointsAgainst:Int = 0) = {
//    stats.cumuPointsFor = seasonStats.runningPointsFor + pointsFor
//    stats.cumuPointsAgainst = seasonStats.runningPointsAgainst + pointsAgainst
//    stats.cumuPointsDifference = seasonStats.runningPointsDifference + stats.pointsDifference
//    seasonStats.runningPointsFor  = stats.cumuPointsFor
//    seasonStats.runningPointsAgainst = stats.cumuPointsAgainst 
//    seasonStats.runningPointsDifference = stats.cumuPointsDifference 
//    stats
//  }
//  
//  def addLeaguePosition(date:Date, leaguePosition:Int) = {
//    val stats = statsForDate(date,false)
//    
//    stats.leaguePosition = leaguePosition
//    seasonStats.currentLeaguePosition = leaguePosition
//  }
//  
//  def statsForDate(date:Date, alwaysNew:Boolean = true) = {
//    val cal = Calendar.getInstance
//    cal.setTime(date)
//    val week = makeWeek(date)
//    
//    if(alwaysNew){
//      weekStats put (week, WeekStats(date))
//      weekStats(week)
//    }
//    else weekStats.getOrElseUpdate(week, updateFromCurrent(WeekStats(date,true)))
//
//  }
//  
//  def makeWeek(date:Date) = {
//     val cal = Calendar.getInstance
//    cal.setTime(date)
//    (cal.get(Calendar.YEAR) *100) + cal.get(Calendar.WEEK_OF_YEAR)
//  }
//  
//
//}
//
//object Statistics{
//  
//  def apply(team:Team, season:Season) = {
//    val stats = new Statistics
//    stats.team  = team
//    stats.season = season
//    stats
//  }
//  
//  def get(team:Team,season:Season):Statistics = {
//    
//    val statSet = entities[Statistics]().filter(s=>s.team.id == team.id && s.season.id == season.id )
//    
//    statSet match {
//      
//      case Nil => {val stats = Statistics(team,season)
//    		  save(stats)
//    		  stats
//      }
//      case _ => statSet.head
//    }
//  }
//}

case class SeasonStats(
  currentLeaguePosition:Int = 0,
  runningPointsFor:Int = 0,
  runningPointsAgainst:Int = 0,
  runningPointsDifference:Int = 0
)

class WeekStats(
  date:LocalDate,
  leaguePosition:Int = 0,
  pointsFor:Int = 0,
  pointsAgainst:Int = 0,
  pointsDifference:Int = 0,
  cumuPointsFor:Int = 0,
  cumuPointsAgainst:Int = 0,
  cumuPointsDifference:Int = 0,
  ignorable:Boolean = false

)





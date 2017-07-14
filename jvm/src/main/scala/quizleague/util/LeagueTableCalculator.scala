package quizleague.util

import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._

object LeagueTableCalculator {
  
  val win = 2
  val draw = 1
  val loss = 0
  
  def Desc[T : Ordering] = implicitly[Ordering[T]].reverse
  
  private def row(team:Ref[Team], result:Result):LeagueTableRow = {
	  
    val score = if(result.fixture.home.id == team.id) result.homeScore else result.awayScore
    val oppoScore = if(result.fixture.away.id == team.id) result.homeScore else result.awayScore
    
    val points = if(score > oppoScore)  win else if(score == oppoScore) draw else loss
		
	  	LeagueTableRow(
	  	    team,
	  	    "",
	  	    1,
	  	    if(points == win)  1 else 0,
	  	    if(points == draw) 1 else 0,
	  	    if(points == loss) 1 else 0,
	  	    points,
	  	    score,
	  	    oppoScore
	  	)
	}
  
  
  def recalculate(table:LeagueTable, results:List[Results]) = {
    
    val teams = table.rows.map(_.team)
    
    val rows = for{team <- teams}
    yield{
      results
      .flatMap(_.results.filter(r => r.fixture.home.id != team.id && r.fixture.away.id == team.id))
      .map(row(team, _))
      .foldLeft(LeagueTableRow(team,"",0,0,0,0,0,0,0))((acc,r) => LeagueTableRow(
          team,
          "",
          acc.played + r.played,
          acc.won + r.won,
          acc.drawn + r.drawn,
          acc.lost + r.lost,
          acc.leaguePoints + r.leaguePoints,
          acc.matchPointsFor + r.matchPointsFor,
          acc.matchPointsAgainst + r.matchPointsAgainst))
    }
    
    LeagueTable(table.id, table.description, rows.sortBy(r => (r.leaguePoints, r.matchPointsFor, r.won, r.drawn))(Desc))
    
  }
  
//   private def addResultToTable(result:Result) = {
//          for(table <- leagueTables) {
//            for(homeRow <- table.rows if(homeRow.team == result.fixture.home)) updateRow(homeRow, result.homeScore, result.awayScore)
//            for(awayRow <- table.rows if(awayRow.team == result.fixture.away)) updateRow(awayRow, result.awayScore, result.homeScore)
//            table.sort
//          }
// 
//  } 
//  
//  def recalculateTables = {
//    
//    for(table <- leagueTables) table.clear()
//    
//    for{resultSet <- results
//        result <- resultSet.get().results    
//    } addResultToTable(result)
//    leagueTables
//  }
	
	
}
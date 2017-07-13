package quizleague.util

import quizleague.conversions.RefConversions._
import quizleague.domain.LeagueTable

object LeagueTableCalculator {
  
  val win = 2
  val draw = 1
  val loss = 0
  
  def recalculate(table:LeagueTable, results:List[Results]) = {
    
    val teams = table.rows.map(_.team)
    
    for{team <- teams}
    yield{
      val res = results.flatMap(_.results.filter(r => r.fixture.home.id != team.id && r.fixture.away.id == team.id))
      res.map()
    }
    
    
    
  }
  
   private def addResultToTable(result:Result) = {
          for(table <- leagueTables) {
            for(homeRow <- table.rows if(homeRow.team == result.fixture.home)) updateRow(homeRow, result.homeScore, result.awayScore)
            for(awayRow <- table.rows if(awayRow.team == result.fixture.away)) updateRow(awayRow, result.awayScore, result.homeScore)
            table.sort
          }
 
  } 
  
  def recalculateTables = {
    
    for(table <- leagueTables) table.clear()
    
    for{resultSet <- results
        result <- resultSet.get().results    
    } addResultToTable(result)
    leagueTables
  }
	
	private def row(team:Ref[Team], score:Int, oppoScore:Int):LeagueTableRow = {
	  	val points = if(score > oppoScore)  win else if(score == oppoScore) draw else loss;
		
	  	LeagueTableRow(
	  	    team,
	  	    "",
	  	    1,
	  	    if(points == win)  1 else 0,
	  	    if(points == draw) 1 else 0,
	  	    if(points == loss) 1 else 0,
	  	    points,
	  	    score,
	  	    oppoScore,
	  	)
	}
}
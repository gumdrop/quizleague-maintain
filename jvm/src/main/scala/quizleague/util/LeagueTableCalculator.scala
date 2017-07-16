package quizleague.util

import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import shapeless._
import quizleague.util.collection.TraversableUtil._
import quizleague.util.collection._

object LeagueTableCalculator {
  
  val positionLens = lens[LeagueTableRow].position
  
  val win = 2
  val draw = 1
  val loss = 0
  
  
  private def row(team:Ref[Team], result:Result)(implicit context:StorageContext):LeagueTableRow = {
	  
    val score = if(result.fixture.home.id == team.id) result.homeScore else result.awayScore
    val oppoScore = if(result.fixture.away.id == team.id) result.homeScore else result.awayScore
    
    val points = if(score > oppoScore)  win else if(score == oppoScore) draw else loss
		
	  	LeagueTableRow(
	  	    team,
	  	    "",
	  	    1,
	  	    if(points == win)  1 else 0,
	  	    if(points == loss) 1 else 0,
	  	    if(points == draw) 1 else 0,
	  	    points,
	  	    score,
	  	    oppoScore
	  	)
	}
  
  
  def recalculate(table:LeagueTable, results:List[Results])(implicit context:StorageContext = StorageContext()) = {
    
    val teams = table.rows.map(_.team)
    
    val rows = teams.map( team =>

      results
      .flatMap(_.results.filter(r => r.fixture.home.id == team.id || r.fixture.away.id == team.id))
      .map(row(team, _))
      .foldLeft(LeagueTableRow(team,"",0,0,0,0,0,0,0))((acc,r) => LeagueTableRow(
          team,
          "",
          acc.played + r.played,
          acc.won + r.won,
          acc.lost + r.lost,
          acc.drawn + r.drawn,
          acc.leaguePoints + r.leaguePoints,
          acc.matchPointsFor + r.matchPointsFor,
          acc.matchPointsAgainst + r.matchPointsAgainst))
    )
    
    LeagueTable(
        table.id, 
        table.description, 
        rows
          .sortBy(r => (r.leaguePoints, r.matchPointsFor, r.won, r.drawn))(Desc)
          .map(doIndexed((i,r) => positionLens.set(r)(s"${i+1}")))      
    )
    
  }
  
}
package quizleague.domain.util

import quizleague.domain._
import quizleague.util.collection._

object LeagueTableRecalculator
{
  
  def recalculate(tables:List[LeagueTable], fixtures:List[Fixture]) = {
    

    
    def makeRows(fixture:Fixture):List[LeagueTableRow] = {
      fixture.result.map(
        r => {
          val homeWin = if(r.homeScore > r.awayScore) 1 else 0
          val awayWin = if(r.awayScore > r.homeScore) 1 else 0
          val draw = if(r.awayScore == r.homeScore) 1 else 0
          
          List(
            LeagueTableRow(fixture.home,"" ,1,homeWin,awayWin,draw, homeWin * 2 + draw,r.homeScore, r.awayScore),
            LeagueTableRow(fixture.away,"" ,1,awayWin,homeWin,draw, awayWin * 2 + draw,r.awayScore, r.homeScore)
        )}    
      ).fold(List[LeagueTableRow]())(identity)
    }
    
    def applyRows(rows:List[LeagueTableRow])(table:LeagueTable):LeagueTable = {
      
      val newRows = table.rows.map(r => {
         rows.filter(_.team == r.team)
         .foldLeft(r)((a,b) => a + b)
      })
      .sortBy(r => (r.leaguePoints,r.matchPointsFor,r.matchPointsAgainst * -1, r.won, r.drawn))(Desc)
      .zipWithIndex
      .map{case(r,i) => r.copy(position = (i + 1).toString())}
      
      table.copy(rows = newRows).withKey(table.key)
    }  
    
    val rows:List[LeagueTableRow] = fixtures.flatMap(makeRows _)
    
    tables.map(applyRows(rows) _)
    

    
 
  }
  
}
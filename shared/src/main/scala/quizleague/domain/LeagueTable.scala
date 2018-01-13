package quizleague.domain

case class LeagueTable(id: String,
  description: String,
  rows: List[LeagueTableRow],
  retired: Boolean = false) extends Entity

case class LeagueTableRow(
  team: Ref[Team],
  position: String,
  played: Int,
  won: Int,
  lost: Int,
  drawn: Int,
  leaguePoints: Int,
  matchPointsFor: Int,
  matchPointsAgainst: Int){
  
  def + (row:LeagueTableRow):LeagueTableRow = {
    LeagueTableRow(
        team, "", 
        played + row.played, 
        won + row.won,
        lost + row.lost,
        drawn + row.drawn,
        leaguePoints + row.leaguePoints,
        matchPointsFor + row.matchPointsFor,
        matchPointsAgainst + row.matchPointsAgainst)
  }
}
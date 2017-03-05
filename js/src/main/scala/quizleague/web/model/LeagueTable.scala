package quizleague.web.model

import angulate2.std.Data
import scalajs.js.Array

@Data
case class LeagueTable(id: String,
  description: String,
  rows: Array[LeagueTableRow]
)

@Data
case class LeagueTableRow(
  team: Team,
  position: String,
  played: Int,
  won: Int,
  lost: Int,
  drawn: Int,
  leaguePoints: Int,
  matchPointsFor: Int,
  matchPointsAgainst: Int
)
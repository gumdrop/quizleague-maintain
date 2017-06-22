package quizleague.web.model

import angulate2.std.Data
import scalajs.js.Array
import quizleague.web.util.rx.RefObservable

@Data
case class LeagueTable(id: String,
  description: String,
  rows: Array[LeagueTableRow]
)

@Data
case class LeagueTableRow(
  team: RefObservable[Team],
  position: String,
  played: Int,
  won: Int,
  lost: Int,
  drawn: Int,
  leaguePoints: Int,
  matchPointsFor: Int,
  matchPointsAgainst: Int
)
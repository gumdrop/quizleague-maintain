package quizleague.web.maintain.util
import scalajs.js
import quizleague.web.model.Team

class TeamManager(var teams: js.Array[Team]) {
  teams = teams.filter(!_.retired)
  
  private var usedTeams = Map[String, Team]()

  def unusedTeams(other: Team) = teams.filter(x => !usedTeams.contains(x.id) && (if (other != js.undefined && other != null) { x.id != other.id } else true))

  def take(team: Team) = { usedTeams = usedTeams + ((team.id, team)); team }
  def untake(team: Team) = usedTeams = usedTeams - team.id
}

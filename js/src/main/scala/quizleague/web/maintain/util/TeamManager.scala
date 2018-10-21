package quizleague.web.maintain.util
import scalajs.js
import quizleague.web.model.Team
import quizleague.web.util.component.SelectWrapper
import quizleague.web.util.rx.RefObservable

class TeamManager(var teams: js.Array[SelectWrapper[Team]]) {
  
  private var usedTeams = Set[String]()

  def unusedTeams(other: RefObservable[Team]) = teams.filter(x => !usedTeams.contains(x.value.id) && (if (other != js.undefined && other != null) { x.value.id != other.id } else true))

  def take(team: RefObservable[Team]) = { usedTeams += team.id; team }
  def untake(team: RefObservable[Team]) = usedTeams -= team.id

}

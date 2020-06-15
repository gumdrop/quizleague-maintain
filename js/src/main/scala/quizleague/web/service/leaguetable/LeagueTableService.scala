package quizleague.web.service.leaguetable


import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{ LeagueTable => Model }
import quizleague.domain.{ LeagueTable => Dom }
import quizleague.domain.{ LeagueTableRow => DomRow }
import quizleague.domain.Ref
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import quizleague.web.service._
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service.user.UserGetService
import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.text.TextGetService
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.user.UserPutService
import quizleague.web.service.fixtures.FixturePutService
import quizleague.web.service.text.TextPutService
import quizleague.web.service.team.TeamPutService
import quizleague.web.service.DirtyListService
import quizleague.web.names.LeagueTableNames
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.web.util.rx.RefObservable
import quizleague.util.collection._



trait LeagueTableGetService extends GetService[Model] with LeagueTableNames {
  override type U = Dom

  val teamService: TeamGetService

  override protected def mapOutSparse(dom: Dom) = Model(dom.id, dom.description, mapRows(dom.rows))

  private def mapRows(rows: List[DomRow]):js.Array[LeagueTableRow] = {

      rows.map(x => LeagueTableRow(refObs(x.team, teamService), x.position, x.played, x.won, x.lost, x.drawn, x.leaguePoints, x.matchPointsFor, x.matchPointsAgainst)).toJSArray
  }

  override protected def dec(json:js.Any) = decodeJson[U](json)

}

trait LeagueTablePutService extends PutService[Model] with LeagueTableGetService{

  override val teamService: TeamPutService

  override protected def mapIn(model: Model) = Dom(
    model.id,
    model.description,
    model.rows.map(r => DomRow(teamService.ref(r.team), r.position, r.played, r.won, r.lost, r.drawn, r.leaguePoints, r.matchPointsFor, r.matchPointsAgainst)).toList)

  override protected def make() = Dom(newId, "", List())

  def rowInstance(team: RefObservable[Team]) = LeagueTableRow(team, "", 0, 0, 0, 0, 0, 0, 0)


  override def enc(item: Dom) = item.asJson
  
  def sortTable(table:Model) = {
    val dom = mapIn(table)
    
    val rows = dom.rows.sortBy(l => (l.leaguePoints, l.matchPointsFor, l.matchPointsAgainst * -1, l.won, l.drawn))(Desc)
    
    mapOutSparse(dom.copy(rows = rows))
    
  }
  
}

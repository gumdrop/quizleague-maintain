package quizleague.web.service.leaguetable

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{ LeagueTable => Model }
import quizleague.domain.{ LeagueTable => Dom }
import quizleague.domain.{ LeagueTableRow => DomRow }
import quizleague.domain.Ref
import rxjs.Observable
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import quizleague.web.service._
import org.threeten.bp.Year
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
import quizleague.web.service.results.ResultsGetService
import io.circe._, io.circe.generic.auto._, io.circe.parser._



trait LeagueTableGetService extends GetService[Model] with LeagueTableNames {
  override type U = Dom

  val teamService: TeamGetService

  override protected def mapOutSparse(dom: Dom) = Model(dom.id, dom.description, mapRows(dom.rows))

  private def mapRows(rows: List[DomRow]):js.Array[LeagueTableRow] = {

      rows.map(x => LeagueTableRow(refObs(x.team, teamService), x.position, x.played, x.won, x.lost, x.drawn, x.leaguePoints, x.matchPointsFor, x.matchPointsAgainst)).toJSArray
  }

  override protected def dec(json:String) = decode[U](json)
  override protected def decList(json:String) = decode[List[U]](json)

}

trait LeagueTablePutService extends PutService[Model] with LeagueTableGetService with DirtyListService[Model] {

  override val teamService: TeamPutService
  val resultsService:ResultsGetService

  override protected def mapIn(model: Model) = Dom(
    model.id,
    model.description,
    model.rows.map(r => DomRow(teamService.ref(r.team), r.position, r.played, r.won, r.lost, r.drawn, r.leaguePoints, r.matchPointsFor, r.matchPointsAgainst)).toList)

  override protected def make() = Dom(newId, "", List())

  def rowInstance(team: Team) = LeagueTableRow(teamService.refObs(team.id), "", 0, 0, 0, 0, 0, 0, 0)

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def enc(item: Dom) = item.asJson
  
  def sortTable(table:Model) = {
    val dom = mapIn(table)
    
    val rows = dom.rows.sortBy(l => (l.leaguePoints * -1, (l.matchPointsFor - l.matchPointsAgainst) * -1, l.won * -1, l.drawn * -1))
    
    mapOutSparse(Dom(dom.id, dom.description, rows, dom.retired))
    
  }
  
  def recalculateTable(competition:Competition, table:Model) = {
    val dom = mapIn(table)
    Observable.zip(competition.results.map(x => resultsService.get(x.id)) :_*).
      map((results,i) => {
        
        dom
        
      })
   /* implement recalculate in map() here*/   
    
  }

}

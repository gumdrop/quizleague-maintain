package quizleague.web.service.team

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Team => DomTeam }
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.names.ComponentNames
import scala.scalajs.js.JSConverters._
import quizleague.web.service._
import quizleague.web.service.venue._
import quizleague.web.service.text._
import quizleague.web.service.user._
import quizleague.web.names.TeamNames

trait TeamGetService extends GetService[Team] with TeamNames {

  override type U = DomTeam

  val venueService: VenueGetService
  val textService: TextGetService
  val userService: UserGetService

  override protected def mapOutSparse(team: DomTeam) = Team(team.id, team.name, team.shortName, refObs(team.venue,venueService), refObs(team.text, textService), team.users.map(u => userService.getRO(u.id)).toJSArray, team.retired)

  override def flush() = { textService.flush(); super.flush() }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def deser(jsonString: String) = decode[DomTeam](jsonString).merge.asInstanceOf[DomTeam]

  def listVenues() = venueService.list().map((l,i) => l.map(v => venueService.refObs(v.id)))
  def listUsers() = userService.list()
}

trait TeamPutService extends PutService[Team] with TeamGetService {

  override val textService: TextPutService
  override val userService: UserPutService
  override val venueService: VenuePutService

  override protected def mapIn(team: Team) = DomTeam(team.id, team.name, team.shortName, venueService.ref(team.venue.id), textService.ref(team.text.id), team.users.map(u => userService.ref(u.id)).toList, team.retired)
  override protected def make() = DomTeam(newId(), "", "", null, textService.getRef(textService.instance()))
  override def save(team: Team) = { log(team.toString(), "team");textService.save(team.text);log(team.venue,"team venue");super.save(team) }
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def ser(item: DomTeam) = item.asJson.noSpaces

}
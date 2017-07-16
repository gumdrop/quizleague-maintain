package quizleague.web.service.team

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Team => Dom }
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.names.ComponentNames
import scala.scalajs.js.JSConverters._
import quizleague.web.service._
import quizleague.web.service.venue._
import quizleague.web.service.text._
import quizleague.web.service.user._
import quizleague.web.names.TeamNames
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._

trait TeamGetService extends GetService[Team] with TeamNames {

  override type U = Dom

  val venueService: VenueGetService
  val textService: TextGetService
  val userService: UserGetService

  override protected def mapOutSparse(team: Dom) = Team(team.id, team.name, team.shortName, refObs(team.venue,venueService), refObs(team.text, textService), team.users.map(u => userService.getRO(u.id)).toJSArray, team.retired)

  override def flush() = { textService.flush(); super.flush() }
  
  protected def dec(json:String) = decode[U](json)
  protected def decList(json:String) = decode[List[U]](json)

  def listVenues() = venueService.list().map((l,i) => l.map(v => venueService.refObs(v.id)))
  def listUsers() = userService.list()
}

trait TeamPutService extends PutService[Team] with TeamGetService {

  override val textService: TextPutService
  override val userService: UserPutService
  override val venueService: VenuePutService

  override protected def mapIn(team: Team) = Dom(team.id, team.name, team.shortName, venueService.ref(team.venue.id), textService.ref(team.text.id), team.users.map(u => userService.ref(u.id)).toList, team.retired)
  override protected def make() = Dom(newId(), "", "", null, textService.getRef(textService.instance()))
  override def save(team: Team) = {textService.save(team.text);super.save(team) }

  override def enc(item: Dom) = item.asJson

}
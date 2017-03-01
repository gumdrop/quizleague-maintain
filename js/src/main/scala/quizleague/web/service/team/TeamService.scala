package quizleague.web.service.team

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Team => DomTeam }
import quizleague.web.maintain.venue.VenueService
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.maintain.component.ComponentNames
import quizleague.web.maintain.user.UserService
import scala.scalajs.js
import quizleague.web.maintain.text.TextService
import quizleague.web.service._
import quizleague.web.service.venue._
import quizleague.web.service.text._
import quizleague.web.service.user._
import quizleague.web.maintain.team.TeamNames

trait TeamGetService extends GetService[Team] with TeamNames {

  override type U = DomTeam

  val venueService: VenueGetService
  val textService: TextGetService
  val userService: UserGetService

  override protected def mapOutSparse(team: DomTeam) = Team(team.id, team.name, team.shortName, null, null, js.Array(), team.retired)
  override protected def mapOut(team: DomTeam)(implicit depth:Int) =
    Observable.zip(
      child(team.venue,venueService),
      child(team.text, textService),
      mapOutList(team.users, userService),
      (venue: Venue, text: Text, users: js.Array[User]) => Team(team.id, team.name, team.shortName, venue, text, users, team.retired))

  override def flush() = { textService.flush(); super.flush() }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def deser(jsonString: String) = decode[DomTeam](jsonString).merge.asInstanceOf[DomTeam]

  def listVenues() = venueService.list()
  def listUsers() = userService.list()
}

trait TeamPutService extends PutService[Team] with TeamGetService {

  override val textService: TextPutService
  override val userService: UserPutService
  override val venueService: VenuePutService

  override protected def mapIn(team: Team) = DomTeam(team.id, team.name, team.shortName, venueService.getRef(team.venue), textService.getRef(team.text), team.users.map(userService.getRef(_)).toList, team.retired)
  override protected def make() = DomTeam(newId(), "", "", null, textService.getRef(textService.instance()))
  override def save(team: Team) = { textService.save(team.text); super.save(team) }
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def ser(item: DomTeam) = item.asJson.noSpaces

}
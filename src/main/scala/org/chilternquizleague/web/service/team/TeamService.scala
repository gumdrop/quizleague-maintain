package org.chilternquizleague.web.service.team

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import org.chilternquizleague.web.model._
import org.chilternquizleague.domain.{ Team => DomTeam }
import org.chilternquizleague.web.maintain.venue.VenueService
import org.chilternquizleague.domain.Ref
import rxjs.Observable
import org.chilternquizleague.web.maintain.component.ComponentNames
import org.chilternquizleague.web.maintain.user.UserService
import scala.scalajs.js
import org.chilternquizleague.web.maintain.text.TextService
import org.chilternquizleague.web.service._
import org.chilternquizleague.web.service.venue._
import org.chilternquizleague.web.service.text._
import org.chilternquizleague.web.service.user._
import org.chilternquizleague.web.maintain.team.TeamNames

trait TeamGetService extends GetService[Team] with TeamNames {

  override type U = DomTeam

  val venueService: VenueGetService
  val textService: TextGetService
  val userService: UserGetService

  override protected def mapOutSparse(team: DomTeam) = Team(team.id, team.name, team.shortName, null, null, js.Array(), team.retired)
  override protected def mapOut(team: DomTeam) =
    Observable.zip(
      venueService.get(team.venue),
      textService.get(team.text),
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
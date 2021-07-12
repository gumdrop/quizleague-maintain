package quizleague.web.service.team

import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Team => Dom }
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import scala.scalajs.js.JSConverters._
import quizleague.web.service._
import quizleague.web.service.venue._
import quizleague.web.service.text._
import quizleague.web.service.user._
import quizleague.web.names.TeamNames
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import scalajs.js

trait TeamGetService extends GetService[Team] with TeamNames {

  override type U = Dom

  val venueService: VenueGetService
  val textService: TextGetService
  val userService: UserGetService

  override protected def mapOutSparse(team: Dom) = Team(team.id, team.name, team.shortName, refObs(team.venue,venueService), refObs(team.text, textService), team.users.map(u => userService.getRO(u.id)).toJSArray, team.retired)

  override def flush() = { textService.flush(); super.flush() }
  
  protected def dec(json:js.Any) = decodeJson[U](json)


}

trait TeamPutService extends PutService[Team] with TeamGetService {

  override val textService: TextPutService
  override val userService: UserPutService
  override val venueService: VenuePutService

  override protected def mapIn(team: Team) = Dom(team.id, team.name, team.shortName, venueService.ref(team.venue.id), textService.ref(team.text.id), team.users.map(u => userService.ref(u.id)).toList, team.retired)
  override protected def make() = withKey(Dom(newId(), "", "", null, textService.getRef(textService.instance())), null)
  override def save(team: Team) = {textService.save(team.text);super.save(team) }

  override def enc(item: Dom) = item.asJson

}
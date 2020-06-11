package quizleague.web.service.user

import quizleague.web.service._
import quizleague.web.model.{Key, SiteUser}
import quizleague.domain.{SiteUser => Dom}
import quizleague.web.names.SiteUserNames
import io.circe.parser._
import io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._

import scalajs.js



trait SiteUserGetService extends GetService[SiteUser] with SiteUserNames {
  override type U = Dom

  val userService: UserGetService

  override protected def mapOutSparse(user: Dom): SiteUser = {
    val siteUser =
    new SiteUser(user.id, user.handle, user.avatar,userService.refObs(user.user), user.uid, user.retired)
    siteUser.key = new Key(null,"siteuser",user.id)
    siteUser
  }

  protected def dec(json:js.Any) = decodeJson[U](json)

}

trait SiteUserPutService extends PutService[SiteUser] with SiteUserGetService {

  val userService: UserPutService

  override protected def mapIn(user: SiteUser) = Dom(user.id, user.handle, user.avatar, userService.refOption(user.user), user.uid, user.retired)

  override protected def make(): Dom = Dom(newId(), "", "",None,None,false)

  override def enc(item: Dom) = item.asJson

}
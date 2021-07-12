package quizleague.web.service.user


import quizleague.web.service._
import quizleague.web.model.User
import quizleague.domain.{ User => Dom }

import quizleague.web.names.UserNames
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import scalajs.js



trait UserGetService extends GetService[User] with UserNames {
  override type U = Dom

  override protected def mapOutSparse(user: Dom): User =
    User(user.id, user.name, user.email, user.retired)

  protected def dec(json:js.Any) = decodeJson[U](json)

}

trait UserPutService extends PutService[User] with UserGetService {
  override protected def mapIn(user: User) = Dom(user.id, user.name, user.email, user.retired)

  override protected def make(): Dom = {
    withKey(Dom(newId(), "", ""), null)
  }

  override def enc(item: Dom) = item.asJson

}
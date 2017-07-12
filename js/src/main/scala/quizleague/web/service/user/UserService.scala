package quizleague.web.service.user

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service._
import quizleague.web.model.User
import quizleague.domain.{ User => Dom }
import rxjs.Observable
import quizleague.web.names.UserNames
  import io.circe._, io.circe.generic.auto._, io.circe.parser._



trait UserGetService extends GetService[User] with UserNames {
  override type U = Dom

  override protected def mapOutSparse(user: Dom): User =
    User(user.id, user.name, user.email, user.retired)

  protected def dec(json:String) = decode[U](json)
  protected def decList(json:String) = decode[List[U]](json)

}

trait UserPutService extends PutService[User] with UserGetService {
  override protected def mapIn(user: User) = Dom(user.id, user.name, user.email, user.retired)

  override protected def make(): Dom = Dom(newId(), "", "")

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def enc(item: Dom) = item.asJson

}
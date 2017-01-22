package org.chilternquizleague.maintain.user

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model.User
import org.chilternquizleague.maintain.domain.{User => DomUser}
import rxjs.Observable

@Injectable
@classModeScala
class UserService(override val http:Http) extends EntityService[User] with UserNames{
  override type U = DomUser
  
  override protected def mapIn(user:User) = DomUser(user.id, user.name, user.email, user.retired)
  override protected def mapOut(user:DomUser):Observable[User] = Observable.of(mapOutSparse(user))
  override protected def mapOutSparse(user:DomUser):User =
    User(user.id, user.name, user.email, user.retired)
  
  override protected def make():DomUser = DomUser(newId(), "","")
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def ser(item:DomUser) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[DomUser](jsonString).merge.asInstanceOf[DomUser]

}
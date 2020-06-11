package quizleague.web.site.user

import quizleague.web.service.user._
import quizleague.web.core._
import quizleague.web.model.SiteUser
import rxscalajs.{Observable, Subject}
import rxscalajs.subjects.ReplaySubject
import org.scalajs.dom
import quizleague.domain.Key
import quizleague.web.model
import quizleague.web.service.PostService

import scalajs.js
import js.JSConverters._


object UserService extends UserGetService with UserPutService{
  
  def userForEmail(email:String) = {
    val lc = email.toLowerCase
    list().map(_.filter(_.email.toLowerCase == lc).headOption)
  }
  
}

object SiteUserService extends SiteUserGetService with SiteUserPutService with PostService{
  val userService = UserService

  def siteUserForEmail(email:String):Observable[Option[SiteUser]] = {
    import quizleague.util.json.codecs.DomainCodecs._
    command[Option[U],String](List("site","site-user-for-email",email),None).map(_.map(u => {
      mapOutSparse(u.withKey(Key(None, typeName, u.id)))}))
  }

  def siteUserForUid(uid:String):Observable[Option[SiteUser]] = {
    query(db.collection(uriRoot).where("uid","==", uid)).map(_.headOption)
  }

  def saveUser(user:SiteUser){
    import quizleague.util.json.codecs.DomainCodecs._
    command[U,Unit](List("site","save-site-user"),Option(user)).subscribe(x => Unit)
  }

  def setUid(user:SiteUser, uid:String): Unit ={
    import model.Key
    val nu = new SiteUser(user.id, user.handle, user.avatar, user.user, Option(uid))
    nu.key = new Key(null, typeName, user.id)
    save(nu)
  }
}




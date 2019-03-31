package quizleague.web.site.user

import quizleague.web.service.user._
import quizleague.web.core._
import quizleague.web.model.SiteUser
import rxscalajs.{Observable, Subject}
import rxscalajs.subjects.ReplaySubject
import org.scalajs.dom
import quizleague.web.service.PostService
import scalajs.js
import js.JSConverters._


object UserService extends UserGetService{
  
  def userForEmail(email:String) = {
    val lc = email.toLowerCase
    list().map(_.filter(_.email.toLowerCase == lc).headOption)
  }
  
}

object SiteUserService extends SiteUserGetService with PostService{
  val userService = UserService

  def siteUserForEmail(email:String):Observable[js.Array[SiteUser]] = {
    import quizleague.util.json.codecs.DomainCodecs._
    command[List[U],String](List("site","site-user-for-email",email),None).map(_.map(mapOutSparse _).toJSArray)
  }

  def saveUser(user:SiteUser) = {}
}

object SiteUserWatchService{

  private val _siteUser:Subject[SiteUser] = ReplaySubject()

  val key = "siteUserID"

  setSiteUserID(dom.window.localStorage.getItem(key))

  def siteUser:Observable[SiteUser] = _siteUser

  def setSiteUserID(id:String)  {
    SiteUserService.get(id).take(1).subscribe(u => {_siteUser.next(u); dom.window.localStorage.setItem("siteUserID", id)})

  }

  def getSiteUserID() = dom.window.localStorage.getItem("siteUserID")

}


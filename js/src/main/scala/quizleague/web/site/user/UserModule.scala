package quizleague.web.site.user

import quizleague.web.service.user._
import quizleague.web.core._
import quizleague.web.model.SiteUser
import rxscalajs.{Observable, Subject}
import rxscalajs.subjects.ReplaySubject
import org.scalajs.dom


object UserService extends UserGetService{
  
  def userForEmail(email:String) = {
    val lc = email.toLowerCase
    list().map(_.filter(_.email.toLowerCase == lc).headOption)
  }
  
}

object SiteUserService extends SiteUserGetService{
  val userService = UserService
}

object SiteUserWatchService{

  private val _siteUser:Subject[SiteUser] = ReplaySubject()

  setSiteUserID(dom.window.localStorage.getItem("siteUserID"))

  def siteUser:Observable[SiteUser] = _siteUser

  def setSiteUserID(id:String)  {
    SiteUserService.get(id).take(1).subscribe(u => {_siteUser.next(u); dom.window.localStorage.setItem("siteUserID", id)})

  }

}


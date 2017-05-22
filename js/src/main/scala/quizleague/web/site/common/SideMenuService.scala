package quizleague.web.site.common

import angulate2.ext.classModeScala
import angulate2.std.Injectable
import rxjs.Subject
import angular.flexlayout.ObservableMedia
import quizleague.web.util.Logging

@Injectable
@classModeScala
class SideMenuService(
   val media:ObservableMedia
) extends Logging 
{
  val showMenu = new Subject[Boolean]
  
  def menuMode = if(media.isActive("xs")) "over" else "side"
  
  def showByDefault = !media.isActive("xs")
  
}
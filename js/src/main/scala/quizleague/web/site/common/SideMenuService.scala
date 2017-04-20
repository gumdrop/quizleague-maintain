package quizleague.web.site.common

import angulate2.ext.classModeScala
import angulate2.std.Injectable
import rxjs.Subject

@Injectable
@classModeScala
class SideMenuService {
  val showMenu = new Subject[Boolean]
  
}
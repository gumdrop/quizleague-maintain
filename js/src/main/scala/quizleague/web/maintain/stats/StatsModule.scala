package quizleague.web.maintain.stats

import quizleague.web.maintain._
import quizleague.web.core._
import org.scalajs.dom.raw.File
import org.scalajs.dom.raw.FileReader
import org.scalajs.dom.raw.UIEvent
import rxscalajs.dom.Request
import rxscalajs.Observable
import rxscalajs.subjects.ReplaySubject
import quizleague.web.util.Logging._
import quizleague.web.service.PostService


object StatsModule extends Module {
  override val routes = @@(
    RouteConfig(path = "/maintain/stats", components = Map("default" -> StatsComponent)))

}


object StatsService extends PostService{
  
  def rebuild(seasonId:String) = {
    
    command[String,String](List("entity","regenerate-stats",seasonId))
    
  }
}

package quizleague.web.site.common

import quizleague.web.site.global.ApplicationContextService
import rxjs.ReplaySubject
import quizleague.web.model.Season
import scala.scalajs.js.annotation.JSExport

trait SeasonSelectService {
    
  protected val applicationContextService:ApplicationContextService

  
  private val seasonSubject = new ReplaySubject[Season](1)
  
  @JSExport
  val season = seasonSubject.filter((s,i) => s != null)
    
  @JSExport
  def seasonChanged(s:Season) = seasonSubject.next(s)
    
  applicationContextService.get.switchMap((ac,i) => ac.currentSeason.obs).subscribe(seasonChanged(_))
}
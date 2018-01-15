package quizleague.web.site.season

import rxscalajs._
import rxscalajs.subjects._
import quizleague.web.site.ApplicationContextService
import quizleague.web.model.Season

trait SeasonWatchService {
  
  private val seasonSubj:Subject[Season] = ReplaySubject()
  
  ApplicationContextService.get().subscribe(_.currentSeason.subscribe(s => seasonSubj.next(s)))
  
  def season = seasonSubj
}
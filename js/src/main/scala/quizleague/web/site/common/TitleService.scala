package quizleague.web.site.common

import angulate2.std._
import quizleague.web.site.global.ApplicationContextService
import angular.core.Title

@Injectable
class TitleService(
    applicationContextService:ApplicationContextService,
    titleService:Title) {
  
  def set(title:String) = applicationContextService.get().subscribe(ac => titleService.setTitle(s"${ac.leagueName} - $title"))
  
}
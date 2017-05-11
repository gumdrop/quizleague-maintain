package quizleague.web.site.common

import angular.core.Title

trait TitledComponent {
  val titleService:TitleService
  
  def setTitle(title:String) = titleService.set(title)
}
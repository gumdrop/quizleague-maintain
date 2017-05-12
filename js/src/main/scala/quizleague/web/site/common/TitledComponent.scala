package quizleague.web.site.common

import angular.core.Title
import angulate2.std._
import angulate2.ext._
import angulate2.core.OnInit

trait TitledComponent {
  val titleService:TitleService
  
  def setTitle(title:String) = titleService.set(title)
}



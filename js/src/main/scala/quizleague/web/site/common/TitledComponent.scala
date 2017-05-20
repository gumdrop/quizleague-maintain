package quizleague.web.site.common

import rxjs.Observable

trait TitledComponent {
  val titleService:TitleService
  
  def setTitle(title:String):Unit = titleService.set(title)
  
  def setTitle(title:Observable[String]):Unit = title.subscribe(t => setTitle(t)) 
}



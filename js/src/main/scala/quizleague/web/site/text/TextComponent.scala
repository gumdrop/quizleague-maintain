package quizleague.web.site.text

import angulate2.std._
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.model._
import rxjs.Observable
import angulate2.core.OnChanges

@Component(
    selector="ql-text",
    template="""
      <div [innerHTML]="(text | async).text" style="font-family:Roboto"></div>
"""
)
class TextComponent (
  textService:TextService
) extends OnChanges {
  
  @Input
  var textId:String = _
  
  var text:Observable[Text] = _
  
  override def ngOnChanges(changes: OnChanges.SimpleChanges) = text = textService.get(textId)

}
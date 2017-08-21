package quizleague.web.site.text

import angulate2.std._
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.model._
import rxjs.Observable
import angulate2.core.OnChanges
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
    selector="ql-text",
    template=s"""
      <div *ngIf="text | async as te; else loading">
        <div *ngIf="te.mimeType=='text/html'" [innerHTML]="te.text"></div>
        <div *ngIf="te.mimeType=='text/plain'" [innerText]="te.text"></div>
      </div>
      $loadingTemplateThin
""",
    styles = @@@("*{font-family:Roboto;}") 
)
class TextComponent (
  textService:TextService
) extends OnChanges {
  
  @Input
  var textId:String = _
  
  var text:Observable[Text] = _
  
  override def ngOnChanges(changes: OnChanges.SimpleChanges) = if(textId != null) text = textService.get(textId)

}
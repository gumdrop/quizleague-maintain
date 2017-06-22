package quizleague.web.site.text

import angulate2.std._
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.model._
import rxjs.Observable

@Component(
    selector="ql-named-text",
    template="""
        <ql-text *ngIf="textId | async as id;else loading" [textId]="id"></ql-text>
        <ng-template #loading>{{name}}</ng-template>
    """
)
class NamedTextComponent (
  applicationContextService:ApplicationContextService
) extends OnInit {
  
  @Input
  var name:String = _
  
  var textId:Observable[String] = _
  
  override def ngOnInit() = {
    
    def get(name:String, globalText:GlobalText):Option[TextEntry] = globalText.text.find(e => e.name == name)
    
    textId = applicationContextService.get.switchMap((ac,i) => ac.textSet.obs).map((t,i) => get(name,t).map(e => e.text.id).getOrElse(null))

  }
  
}
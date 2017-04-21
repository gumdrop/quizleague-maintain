package quizleague.web.site.text

import angulate2.std._
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.model._
import rxjs.Observable

@Component(
    selector="ql-named-text",
    template="""<ql-text [textId]="textId | async"></ql-text>"""
)
class NamedTextComponent (
  applicationContextService:ApplicationContextService
) extends OnInit {
  
  @Input
  var name:String = _
  
  var textId:Observable[String] = _
  
  override def ngOnInit() = {
    
    def get(name:String, globalText:GlobalText):Option[TextEntry] = globalText.text.find(e => e.name == name)
    
    textId = applicationContextService.get.map((apc,i) => get(name,apc.textSet).map(e => e.text.id).get)

  }
  
}
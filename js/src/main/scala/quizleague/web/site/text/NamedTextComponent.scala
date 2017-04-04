package quizleague.web.site.text

import angulate2.std._
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.model._

@Component(
    selector="ql-named-text",
    template="""
      <div [innerHTML]="text" style="font-family:Roboto"></div>
"""
)
class NamedTextComponent (
  applicationContextService:ApplicationContextService,
  textService:TextService
) extends OnInit {
  
  @Input
  var name:String = _
  
  var text:String = _
  
  override def ngOnInit() = {
    
    def get(name:String, globalText:GlobalText):Option[TextEntry] = globalText.text.find(e => e.name == name)
    
    applicationContextService.get.subscribe( apc => get(name,apc.textSet).foreach{
      (e:TextEntry) => textService.get(e.text.id).subscribe( (t:Text) => text = t.text)
    })
  }
  
}
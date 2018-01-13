package quizleague.web.site.text

import quizleague.web.core._
import quizleague.web.model.GlobalText
import quizleague.web.model.TextEntry
import quizleague.web.site.ApplicationContextService
import com.felstar.scalajs.vue._
import scalajs.js

//
//import angulate2.std._
//import quizleague.web.site.global.ApplicationContextService
//import quizleague.web.model._
//import rxjs.Observable

//@Component(
//    selector="ql-named-text",
//    template="""
//        <ql-text *ngIf="textId | async as id;else loading" [textId]="id"></ql-text>
//        <ng-template #loading>{{name}}</ng-template>
//    """
//)
//class NamedTextComponent (
//  applicationContextService:ApplicationContextService
//) extends OnInit {
//  
//  @Input
//  var name:String = _
//  
//  var textId:Observable[String] = _
//  
//  override def ngOnInit() = {
//    
//    def get(name:String, globalText:GlobalText):Option[TextEntry] = globalText.text.find(e => e.name == name)
//    
//    textId = applicationContextService.get.switchMap((ac,i) => ac.textSet.obs).map((t,i) => get(name,t).map(e => e.text.id).getOrElse(null))
//
//  }
//  
//}

@js.native
trait NamedText extends VueComponent with VueRxComponent{
  val name:String = js.native
}

object NamedTextComponent extends Component{
  
  type facade = NamedText
  val name = "ql-named-text"
  val template = """
        <ql-text v-if="textId" :id="textId"></ql-text>
    """
  props("name")
  
  subscription("textId","name")(c => ApplicationContextService.get.flatMap(ac => ac.textSet.obs).map(t => get(c.name,t).map(e => e.text.id).getOrElse(null)))
  
  def get(name:String, globalText:GlobalText):Option[TextEntry] = globalText.text.find(e => e.name == name)
}
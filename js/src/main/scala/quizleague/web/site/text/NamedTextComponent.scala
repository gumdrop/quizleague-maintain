package quizleague.web.site.text

import quizleague.web.core._
import quizleague.web.model.GlobalText
import quizleague.web.model.TextEntry
import quizleague.web.site.ApplicationContextService
import com.felstar.scalajs.vue._
import scalajs.js

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
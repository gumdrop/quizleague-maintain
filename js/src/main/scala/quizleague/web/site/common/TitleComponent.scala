package quizleague.web.site.common

import quizleague.web.core._
import quizleague.web.site.ApplicationContextService
import scalajs.js
import com.felstar.scalajs.vue._

@js.native
trait TitleComponent extends VueRxComponent{
  val leagueName:String
}

object TitleComponent extends Component{
  type facade = TitleComponent
  val name = "ql-title"
  val template = """<span style="display:none;"><slot></slot></span>"""
  
  override val updated = ({(c:facade) => TitleService.title = s"${c.leagueName} - ${c.$slots.default.asInstanceOf[js.Array[js.Dynamic]](0).text.toString}"}:js.ThisFunction)
  
  subscription("leagueName")(c => ApplicationContextService.get.map(_.leagueName))
}
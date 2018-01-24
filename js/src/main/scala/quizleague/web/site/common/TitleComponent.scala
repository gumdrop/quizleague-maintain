package quizleague.web.site.common

import quizleague.web.core._
import quizleague.web.site.ApplicationContextService
import scalajs.js
import com.felstar.scalajs.vue._
import scala.scalajs.js.UndefOr
import UndefOr._

@js.native
trait TitleComponent extends VueRxComponent{
  val leagueName:String
}

object TitleComponent extends Component{
  type facade = TitleComponent
  val name = "ql-title"
  val template = """<span style="display:none;" v-if="leagueName"><slot></slot><slot name="dummy">{{leagueName}}{{setTitle()}}</slot></span>"""
  
 
  def setTitle(c:facade){
    def doit(name:UndefOr[String], text:UndefOr[js.Any]){
       for(n <- name;t <- text) TitleService.title = s"$n - $t"
    }
    
    doit(c.leagueName, c.$slots.default.asInstanceOf[js.Array[js.Dynamic]](0).text)
    
  }

  //override val updated = ({setTitle _}:js.ThisFunction)
  //override val mounted = ({setTitle _}:js.ThisFunction)
  subscription("leagueName")(c => ApplicationContextService.get.map(_.leagueName))
  method("setTitle")({setTitle _}:js.ThisFunction)
      
}
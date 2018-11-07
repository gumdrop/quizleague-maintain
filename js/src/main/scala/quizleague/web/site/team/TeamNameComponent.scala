package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.IdComponent
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.util.rx.RefObservable
import quizleague.web.model.Team

import scala.scalajs.js.UndefOr
import scalajs.js

@js.native
trait TeamNameComponent extends IdComponent{
  val team:UndefOr[RefObservable[Team]]
}

object TeamNameComponent extends Component{
  
  type facade = TeamNameComponent
  val name = "ql-team-name"
  val template = """<span v-if="t">{{short ? t.shortName : t.name}}</span>"""
  props("team","short","id")
  subscription("t","team", "id")(c => c.team.toOption.fold(TeamService.get(c.id))(_.obs))

  
}

object ResponsiveTeamNameComponent extends Component{
  
  val name = "ql-r-team-name"
  val template = """<span >{{$vuetify.breakpoint.xsOnly ? team.shortName : team.name}}</span>"""
  props("team")

  
}
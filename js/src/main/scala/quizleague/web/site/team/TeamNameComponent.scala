package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.IdComponent
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.util.rx.RefObservable
import quizleague.web.model.Team
import quizleague.web.site.team.TeamNameComponent.subscription
import rxscalajs.Observable

import scala.scalajs.js.UndefOr
import scalajs.js

@js.native
trait TeamNameComponent extends IdComponent{
  val team:UndefOr[RefObservable[Team]]
}

object TeamNameComponent extends Component{
  
  type facade = TeamNameComponent
  val name = "ql-team-name"
  val template = """<span><v-skeleton-loader v-if="!t" type="text" width="15em"></v-skeleton-loader><span v-else>{{short ? t.shortName : t.name}}</span></span>"""
  props("team","short","id")
  subscription("t","team", "id")(c => c.team.toOption.fold(TeamService.get(c.id))(_.obs))
}


@js.native
trait ResponsiveTeamNameComponent extends IdComponent{
  val team:UndefOr[Team]
}

object ResponsiveTeamNameComponent extends Component{
  type facade = ResponsiveTeamNameComponent
  val name = "ql-r-team-name"
  val template = """<span><v-skeleton-loader v-if="!t" type="text" width="15em"></v-skeleton-loader><span v-else>{{$vuetify.breakpoint.smAndDown ? t.shortName : t.name}}</span></span>"""
  props("team","id")
  subscription("t","team", "id")(c => c.team.toOption.fold(TeamService.get(c.id))(x => Observable.just(x)))

  
}
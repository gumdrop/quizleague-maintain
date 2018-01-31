package quizleague.web.useredit

import scala.scalajs.js
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.core._
import quizleague.web.model.Team
import quizleague.web.site.text.TextService
import quizleague.web.site.team.TeamService

object TeamEditPage extends RouteComponent{
  
  val template = """
    <ql-team-edit :id="$route.params.id"></ql-team-edit>
    
    """
  components(TeamEditComponent)
  
}


@js.native
trait TeamEditComponent extends IdComponent{
  var text:String
  val team:Team
}
object TeamEditComponent extends Component with GridSizeComponentConfig{
  
  type facade = TeamEditComponent
  val name = "ql-team-edit"   
  val template="""
      <v-container v-bind="gridSize" fluid>
        <v-layout column v-if="team">
          <v-form>
            <v-text-field label="Name" v-model="team.name" length="20"></v-text-field>
            <v-text-field label="Short Name" v-model="team.shortName" length="10"></v-text-field>
            <text-edit :textId="team.text.id"></text-edit>
  		    </v-form>
        </v-layout>
       </v-container>"""
  components(TextEditComponent)
  
  props("id")
  subscription("team","id")(v => TeamService.get(v.id))
  
}

@js.native
trait TextEditComponent extends VueRxComponent{
  val textId:String
}

object TextEditComponent extends Component{
  
  type facade = TextEditComponent
  
  val name="text-edit"
  
  val template = """
    
    <quill-editor v-if="text" v-model="text.text"></quill-editor>"""
  
  props("textId")
  subscription("text")(c => TextService.get(c.textId))
}
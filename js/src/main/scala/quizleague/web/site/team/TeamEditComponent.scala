package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.model._
import scalajs.js

object TeamEditPage extends RouteComponent{
  
  val template = """
    <ql-team-edit :id="$route.params.id"></ql-team-edit>
    
    """
  
  
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
            <v-text-field label="Name" v-model="team.name"></v-text-field>
            <v-text-field label="Short Name" v-model="team.shortName"></v-text-field>
            <quill-editor v-model="text">
  		      </quill-editor>
  		    </v-form>
        </v-layout>
       </v-container>"""
  props("id")
  subscription("team","id")(v => TeamService.get(v.id))
  
}
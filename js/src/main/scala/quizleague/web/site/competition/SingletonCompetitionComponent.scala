package quizleague.web.site.competition

import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig

import scala.scalajs.js


object SingletonCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id" ></competition>"""
  components(SingletonCompetitionComponent)
}


object SingletonCompetitionComponent extends Component with GridSizeComponentConfig{
  
  type facade = IdComponent
  
  val name = "competition"
  
  val template = """ 
<v-container v-bind="gridSize" v-if="item" fluid>
    <v-layout column >

    <v-flex align-content-start><ql-named-text :name="item.textName"></ql-named-text></v-flex>
    <div>
      <v-btn href="#" v-scroll-to="'#chat'" flat color="primary"><v-icon left>chat</v-icon>Chat</v-btn>
    </div>
      <v-card>
       <v-card-text>
        <div><b>This season's competition will take place at <a :to="'/venue/' + item.event.venue.id">{{async(item.event.venue).name}}</a> on {{item.event.date | date("d MMMM yyyy")}} starting at {{item.event.time}}</b> </div>
        <br>
        <ql-text :id="item.text.id"></ql-text>
        </v-card-text>
      </v-card>
     <v-card class="mt-3"  id="chat">
        <v-card-title>Chat</v-card-title>
          <v-card-text>
            <ql-chat :parentKey="parentKey"></ql-chat>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <ql-login-button label="Login for chat" ></ql-login-button>
          </v-card-actions>
      </v-card>
  </v-layout>
</v-container>"""
  
  props("id")
  
  subscription("item","id")(c => CompetitionService.get(c.id))
  computed("parentKey")({c:facade => CompetitionService.key(c.id)}:js.ThisFunction)

}
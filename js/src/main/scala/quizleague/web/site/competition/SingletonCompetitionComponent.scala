package quizleague.web.site.competition

import quizleague.web.core._
import KeyComponent._
import quizleague.web.core.GridSizeComponentConfig

import scala.scalajs.js


object SingletonCompetitionPage extends RouteComponent{
  val template = """<competition :keyval="decode($route.params.key)" ></competition>"""
  components(SingletonCompetitionComponent)
}


object SingletonCompetitionComponent extends Component with GridSizeComponentConfig{
  
  type facade = KeyComponent
  
  val name = "competition"
  
  val template = """ 
<v-container v-bind="gridSize" v-if="item" fluid>
    <v-layout column >

    <v-flex align-content-start><ql-text-box><ql-named-text :name="item.textName"></ql-named-text></ql-text-box></v-flex>
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
            <ql-chat :parentKey="keyval"></ql-chat>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <ql-login-button label="Login for chat" ></ql-login-button>
          </v-card-actions>
      </v-card>
  </v-layout>
</v-container>"""
  
  props("keyval")
  
  subscription("item","keyval")(c => CompetitionService.get(key(c)))
}
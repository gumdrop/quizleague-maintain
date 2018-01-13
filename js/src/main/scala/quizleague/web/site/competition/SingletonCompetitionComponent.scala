package quizleague.web.site.competition

import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig


object SingletonCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id" ></competition>"""
  components(SingletonCompetitionComponent)
}


object SingletonCompetitionComponent extends Component with GridSizeComponentConfig{
  
  type facade = IdComponent
  
  val name = "competition"
  
  val template = """ 
<v-container v-bind="gridSize" fluid v-if="item">
    <v-layout column >
    <v-flex><ql-named-text :name="item.textName"></ql-named-text></v-flex>
    <v-flex>
      <v-card>
       <v-card-text>
        <div><b>This season's competition will take place at <a :to="'/venue/' + item.event.venue.id">{{async(item.event.venue).name}}</a> on {{item.event.date | date("d MMMM yyyy")}} starting at {{item.event.time}}</b> </div>
        <br>
        <ql-text :id="item.text.id"></ql-text>
        </v-card-text>
      </v-card> 
    </v-flex>
  </v-layout>
</v-container>"""
  
 props("id")
  
 subscription("item","id")(c => CompetitionService.get(c.id))
      

  
}
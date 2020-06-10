package quizleague.web.site.competition


import quizleague.web.core._
import KeyComponent._
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.site.SideMenu



object BeerCompetitionPage extends RouteComponent{
  val template = """<competition :keyval="decode($route.params.key)"></competition>"""
  components(BeerCompetitionComponent)
}

object BeerCompetitionComponent extends Component with GridSizeComponentConfig{
  type facade = KeyComponent
  val name = "competition"
  val template = """
  <v-container v-bind="gridSize" fluid v-if="item">
    <v-layout column v-bind="gridSize">
      <v-flex>      
        <ql-text-box>
        <ql-named-text :name="item.textName"></ql-named-text>
        <ql-text :id="item.text.id"></ql-text>
        </ql-text-box>
      </v-flex>
      <v-flex><league-tables :keyval="keyval"></league-tables></v-flex>
      <v-flex><latest-results :keyval="keyval"></latest-results></v-flex>
    </v-layout>
   </v-container>
"""
  
  props("keyval")
 
  subscription ("item","keyval")(c => CompetitionService.get(key(c)))
      
  components (LatestResults, LeagueTables)
}

package quizleague.web.site.competition


import quizleague.web.core._
import KeyComponent._
import quizleague.web.core.GridSizeComponentConfig

object CupCompetitionPage extends RouteComponent{
  val template = """<competition :keyval="decode($route.params.key)" text-name="cup-comp"></competition>"""
  components(KnockoutCompetitionComponent)
}


object KnockoutCompetitionComponent extends Component with GridSizeComponentConfig{
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
      <v-flex><latest-results :keyval="keyval"></latest-results></v-flex>
      <v-flex><next-fixtures :keyval="keyval"></next-fixtures></v-flex>
    </v-layout>
   </v-container>
"""
  
  props("keyval", "textName")
 
  subscription("item","keyval")(c => CompetitionService.get(key(c)))
      
  components(LatestResults,NextFixtures, LeagueTables)
}

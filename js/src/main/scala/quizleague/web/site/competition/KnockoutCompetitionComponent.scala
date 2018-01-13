package quizleague.web.site.competition


import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig

object CupCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id" text-name="cup-comp"></competition>"""
  components(KnockoutCompetitionComponent)
}


object KnockoutCompetitionComponent extends Component with GridSizeComponentConfig{
  type facade = IdComponent
  val name = "competition"
  val template = """
  <v-container v-bind="gridSize" fluid v-if="item">
    <v-layout column v-bind="gridSize">
      <v-flex>      
        <ql-named-text :name="item.textName"></ql-named-text>
        <ql-text :id="item.text.id"></ql-text>
      </v-flex>
      <v-flex><latest-results :id="id"></latest-results></v-flex>
      <v-flex><next-fixtures :id="id"></next-fixtures></v-flex>
    </v-layout>
   </v-container>
"""
  
  props("id", "textName")
 
  subscription("item","id")(c => CompetitionService.get(c.id))
      
  components(LatestResults,NextFixtures, LeagueTables)
}

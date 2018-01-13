package quizleague.web.site.competition


import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig

object LeagueCompetitionPage extends RouteComponent{
  val template = """<ql-league-competition :id="$route.params.id"></ql-league-competition>"""
  components(LeagueCompetitionComponent)
}

object LeagueCompetitionComponent extends Component with GridSizeComponentConfig{
  type facade = IdComponent
  val name = "ql-league-competition"
  val template = """
  <v-container v-bind="gridSize" v-if="item">
    <v-layout column>
      <v-flex>      
        <ql-named-text :name="item.textName"></ql-named-text>
        <ql-text :id="item.text.id"></ql-text>
      </v-flex>
      <v-flex><league-tables :id="id"></league-tables></v-flex>
      <v-flex><latest-results :id="id"></latest-results></v-flex>
      <v-flex><next-fixtures :id="id"></next-fixtures></v-flex>
    </v-layout>
   </v-container>
"""
  
  props("id")
  
  subscription("item","id")(c => CompetitionService.get(c.id))
      
  components(LatestResults,NextFixtures, LeagueTables)
}

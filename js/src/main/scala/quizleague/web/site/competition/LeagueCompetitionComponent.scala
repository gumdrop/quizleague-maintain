package quizleague.web.site.competition


import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig
import KeyComponent._
import quizleague.web.model.Key

object LeagueCompetitionPage extends RouteComponent{
  val template = """<ql-league-competition :keyval="decode($route.params.key)">{{$route.params.key}}</ql-league-competition>"""
  components(LeagueCompetitionComponent)
}

object LeagueCompetitionComponent extends Component with GridSizeComponentConfig{
  type facade = KeyComponent
  val name = "ql-league-competition"
  val template = """
  <v-container v-bind="gridSize" v-if="item" fluid>
    <v-layout column>
      <v-flex>
        <ql-text-box>
        <ql-named-text :name="item.textName"></ql-named-text>
        <ql-text :id="item.text.id"></ql-text>
        </ql-text-box>
      </v-flex>
      <v-flex><league-tables :keyval="keyval"></league-tables></v-flex>
      <v-flex><latest-results :keyval="keyval"></latest-results></v-flex>
      <v-flex><next-fixtures :keyval="keyval"></next-fixtures></v-flex>
    </v-layout>
   </v-container>
"""
  
  props("keyval")
  
  subscription("item","keyval")(c => CompetitionService.get(key(c)))
      
  components(LatestResults,NextFixtures, LeagueTables)
}

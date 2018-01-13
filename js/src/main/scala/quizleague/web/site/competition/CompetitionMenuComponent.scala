package quizleague.web.site.competition

import quizleague.web.site.ApplicationContextService
import quizleague.web.core._
import quizleague.web.site.season.SeasonIdComponent
import scalajs.js
import js.JSConverters._
import quizleague.web.model.Competition


object CompetitionMenu extends RouteComponent{
  val template = """
        <ql-competition-menu></ql-competition-menu>
  """
  
  components(CompetitionMenuComponent)
}

object CompetitionMenuComponent extends Component{
  type facade = SeasonIdComponent
  val name = "ql-competition-menu"
  val template = """
    <v-list dense v-if="competitions && season">
     <v-list-group no-action :value="true">
            <v-list-tile slot="item" @click="">
              <v-list-tile-action>
                <v-icon>mdi-trophy</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>Competitions {{season.startYear}}/{{season.endYear}}</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-icon>keyboard_arrow_down</v-icon>
              </v-list-tile-action>
            </v-list-tile>
       <v-list-tile v-for="competition in sort(competitions)" :key="competition.id" left>
          <v-btn :to="'/competition/' + competition.id +'/' + competition.typeName" flat left><v-icon v-id="competition.icon" left>{{competition.icon}}</v-icon><span>{{competition.name}}</span></v-btn>
      </v-list-tile>
    </v-list-group>
    </v-list>
    """

  subscription("competitions")(c => CompetitionViewService.competitions())
  subscription("season")(c => CompetitionViewService.season)
  method("sort")((comps:js.Array[Competition]) => comps.sortBy(_.name))
}
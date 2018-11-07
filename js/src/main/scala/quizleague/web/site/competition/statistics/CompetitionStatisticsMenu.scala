package quizleague.web.site.competition.statistics

import quizleague.web.core._
import quizleague.web.site.SideMenu


object CompetitionStatisticsMenuComponent  extends RouteComponent with SideMenu{

  val template = """<comp-stats-menu></comp-stats-menu>"""

  components(CompetitionStatisticsMenu)

}


object CompetitionStatisticsMenu  extends Component{

  val name = "comp-stats-menu"
  val template = """
      <ql-side-menu title="Roll Of Honour" icon="mdi-script">
       <v-list-tile v-for="stats in rollofhonour" :key="stats.id" :to="'/competition/rollofhonour/' + stats.id">
           <v-list-tile-content><v-list-tile-title>{{stats.competitionName}}</v-list-tile-title></v-list-tile-content>
       </v-list-tile>
    </ql-side-menu>
  """

  subscription("rollofhonour")(c => CompetitionStatisticsService.list().map(_.sortBy(_.competitionName)))

}

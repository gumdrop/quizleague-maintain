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
       <v-list-item v-for="stats in rollofhonour" :key="stats.id" :to="'/competition/rollofhonour/' + stats.id">
           <v-list-item-content><v-list-item-title>{{stats.competitionName}}</v-list-item-title></v-list-item-content>
       </v-list-item>
    </ql-side-menu>
  """

  subscription("rollofhonour")(c => CompetitionStatisticsService.list().map(_.sortBy(_.competitionName)))

}

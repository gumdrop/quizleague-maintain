package quizleague.web.site.competition

import quizleague.web.site.ApplicationContextService
import quizleague.web.core._
import quizleague.web.site.season._

import scalajs.js
import js.JSConverters._
import quizleague.web.model.Competition
import quizleague.web.site.SideMenu
import quizleague.web.site.competition.statistics.{CompetitionStatisticsMenuComponent, CompetitionStatisticsService}
import statistics._


object CompetitionMenu extends RouteComponent with SideMenu{
  val template = """
        <ql-competition-menu></ql-competition-menu>
  """
  
  components(CompetitionMenuComponent)
}

object CompetitionMenuComponent extends Component with SeasonFormatComponent{
  type facade = SeasonIdComponent
  val name = "ql-competition-menu"
  val template = """
    <div>
    <ql-side-menu :title="'Competitions ' + formatSeason(season)" icon="mdi-trophy" v-if="competitions && season">
       <v-list-tile v-for="competition in competitions" :key="competition.id" :to="'/competition/' + competition.id +'/' + competition.typeName">
          <v-list-tile-action><v-icon  text left v-if="competition.icon">{{competition.icon}}</v-icon></v-list-tile-action>
          <v-list-tile-content><v-list-tile-title>{{competition.name}}</v-list-tile-title></v-list-tile-content>
      </v-list-tile>
    </ql-side-menu>
    <v-divider></v-divider>
    <comp-stats-menu></comp-stats-menu>
    </div>
    """
  components(CompetitionStatisticsMenu)

  subscription("competitions")(c => CompetitionViewService.competitions().map(_.sortBy(_.name)))
  subscription("season")(c => CompetitionViewService.season)
}
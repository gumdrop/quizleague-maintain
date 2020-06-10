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
       <v-list-item v-for="competition in competitions" :key="competition.id" :to="'/competition/'+ competition.key.encode + '/' + competition.typeName">
          <v-list-item-action><v-icon  text left v-if="competition.icon">{{competition.icon}}</v-icon></v-list-item-action>
          <v-list-item-content><v-list-item-title>{{competition.name}}</v-list-item-title></v-list-item-content>
      </v-list-item>
    </ql-side-menu>
    <v-divider></v-divider>
    <comp-stats-menu></comp-stats-menu>
    </div>
    """
  components(CompetitionStatisticsMenu)

  subscription("competitions")(c => CompetitionViewService.competitions().map(_.sortBy(_.name)))
  subscription("season")(c => CompetitionViewService.season)
}
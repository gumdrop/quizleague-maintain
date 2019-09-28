package quizleague.web.site.competition.statistics

import quizleague.web.core.{Component, IdComponent, RouteComponent}
import quizleague.web.site.season.SeasonFormatComponent


object CompetitionStatisticsTitle extends RouteComponent{
  val template = """<competition-statistics-title :id="$route.params.id" :text="null"></competition-statistics-title>"""
  components(CompetitionStatisticsTitleComponent)
}

object CompetitionStatisticsTitleComponent extends Component{
  type facade = IdComponent
  val name = "competition-statistics-title"
  val template = """
    <v-toolbar
      color="purple lighten-3"
      dense
      class="subtitle-background"
      >
      <v-toolbar-title v-if="item" >
      Roll Of Honour - {{item.competitionName}}
       <ql-title>Roll Of Honour - {{item.competitionName}}</ql-title>
      </v-toolbar-title>
    </v-toolbar>"""

  props("id")
  subscription("item","id")(c => CompetitionStatisticsService.get(c.id))

}

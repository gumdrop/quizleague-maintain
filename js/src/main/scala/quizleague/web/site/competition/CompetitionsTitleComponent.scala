package quizleague.web.site.competition

import quizleague.web.core.RouteComponent
import quizleague.web.site.season.SeasonFormatComponent


object CompetitionsTitleComponent extends RouteComponent with SeasonFormatComponent{
  val template = """<v-toolbar      
      color="purple darken-3"
      dark
      >
      <ql-title v-if="s">Competitions {{formatSeason(s)}}</ql-title>
      <v-toolbar-title class="white--text" >
        Competitions
      </v-toolbar-title>
      &nbsp;<h3><ql-season-select :season="season"></ql-season-select></h3>
    </v-toolbar>"""
  
  data("season",CompetitionViewService.season)
  subscription("s")(c => CompetitionViewService.season)
}

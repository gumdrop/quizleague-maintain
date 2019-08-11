package quizleague.web.site.competition

import quizleague.web.core.RouteComponent
import quizleague.web.site.season.SeasonFormatComponent


object CompetitionsTitleComponent extends RouteComponent with SeasonFormatComponent{
  val template = """<v-toolbar      
      color="purple darken-3"
      dark
      dense
      >
      <ql-title v-if="s">Competitions {{formatSeason(s)}}</ql-title>
      <v-toolbar-title class="white--text" >
        Competitions
      </v-toolbar-title>
       <span style="padding-left:2em;"></span>
      <v-toolbar-items>
        <ql-season-select :season="season" :inline="true"></ql-season-select>
      </v-toolbar-items>
    </v-toolbar>"""
  
  data("season",CompetitionViewService.season)
  subscription("s")(c => CompetitionViewService.season)
}

package quizleague.web.site.competition

import quizleague.web.core.RouteComponent


object CompetitionsTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="purple darken-3"
      dark
      clipped-left>
      <ql-title v-if="s">Competitions {{s.startYear}}/{{s.endYear}}</ql-title>
      <v-toolbar-title class="white--text" >
        Competitions
      </v-toolbar-title>
      &nbsp;<h3><ql-season-select :season="season"></ql-season-select></h3>
    </v-toolbar>"""
  
  data("season",CompetitionViewService.season)
  subscription("s")(c => CompetitionViewService.season)
}

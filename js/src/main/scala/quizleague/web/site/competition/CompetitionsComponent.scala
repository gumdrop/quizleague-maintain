package quizleague.web.site.competition

import quizleague.web.core._

object CompetitionsComponent extends RouteComponent with GridSizeComponentConfig {
    val template="""
      <v-container v-bind="gridSize" fluid>
        <v-layout>
          <v-flex><ql-named-text name="competitions-header"></ql-named-text></v-flex>
        </v-layout>
       </v-container>"""

}
package quizleague.web.site.fixtures

import quizleague.web.core.RouteComponent

import quizleague.web.core._
import quizleague.web.site.season.SeasonIdComponent
import quizleague.web.site.ApplicationContextService
import rxscalajs.Observable
import scalajs.js
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.core.GridSizeComponentConfig
import com.felstar.scalajs.vue.VuetifyComponent




object AllFixturesPage extends RouteComponent{
  val template = """
                      <ql-all-fixtures v-if="appData" :seasonId="appData.currentSeason.id"></ql-all-fixtures>
                    """
    subscription("appData")(c => ApplicationContextService.get)
}


object AllFixturesComponent extends Component with GridSizeComponentConfig{
  val name = "ql-all-fixtures"
  type facade = SeasonIdComponent with VuetifyComponent
  val template = """
    <v-container v-if="fixtures" v-bind="gridSize" fluid>
    <v-layout column>
      <v-flex v-for="fixs in fixtures" :key="fixs.id">
        <v-card>
          <v-card-title primary-title><h3 class="headline mb-0">{{fixs.date | date('d MMM yyyy')}} {{fixs.description}}</h3></v-card-title>
          <v-card-text>
            <ql-fixtures-simple :fixtures="fixs.fixtures| combine"></ql-fixtures-simple>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    </v-container>"""
  
  subscription("fixtures")(c => FixturesService.activeFixtures(c.seasonId))
  props("seasonId")
  
}

object AllFixturesTitleComponent extends RouteComponent{
  val template = """
    <v-toolbar      
      color="red darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" >
        All Fixtures
      </v-toolbar-title>
    </v-toolbar>"""
}

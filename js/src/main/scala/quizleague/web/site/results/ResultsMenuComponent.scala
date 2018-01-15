package quizleague.web.site.results

import scalajs.js
import quizleague.web.core.RouteComponent


object ResultsMenuComponent extends RouteComponent{
  val template = """
  <v-list dense >
      <v-list-group no-action :value="true">
            <v-list-tile slot="item" @click="">
              <v-list-tile-action>
                <v-icon>check</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>Results</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-icon>keyboard_arrow_down</v-icon>
              </v-list-tile-action>
            </v-list-tile>
    <v-list-tile >
      <v-btn to="/results/all" flat >All Results</v-btn>
    </v-list-tile>
    <v-list-tile >
      <v-btn to="/fixtures/all" flat >All Fixtures</v-btn>
    </v-list-tile>
        <v-list-tile >
      <v-btn to="/results/submit" flat >Submit Results</v-btn>
    </v-list-tile>
      </v-list-group>
   </v-list>
  """    
}
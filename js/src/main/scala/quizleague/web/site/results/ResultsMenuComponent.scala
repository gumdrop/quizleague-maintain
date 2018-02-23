package quizleague.web.site.results

import scalajs.js
import quizleague.web.core.RouteComponent
import quizleague.web.site.SideMenu


object ResultsMenuComponent extends RouteComponent with SideMenu{
  val template = """
  <ql-side-menu title="Results" icon="check">
  <v-list-tile to="/results/all">
      <v-list-tile-content><v-list-tile-title   >All Results</v-list-tile-title></v-list-tile-content>
    </v-list-tile>
    <v-list-tile to="/fixtures/all">
      <v-list-tile-content><v-list-tile-title   >All Fixtures</v-list-tile-title></v-list-tile-content>
    </v-list-tile>
        <v-list-tile to="/results/submit" >
      <v-list-tile-content><v-list-tile-title  >Submit Results</v-list-tile-title></v-list-tile-content>
    </v-list-tile>
  </ql-side-menu>
  """    
}
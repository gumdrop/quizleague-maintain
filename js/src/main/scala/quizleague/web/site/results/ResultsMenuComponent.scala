package quizleague.web.site.results

import scalajs.js
import quizleague.web.core.RouteComponent
import quizleague.web.site.SideMenu
import quizleague.web.site.login.LoginService


object ResultsMenuComponent extends RouteComponent with SideMenu{
  val template = """
  <ql-side-menu title="Results" icon="mdi-check">
  <v-list-item to="/results/all">
      <v-list-item-content><v-list-item-title   >All Results</v-list-item-title></v-list-item-content>
    </v-list-item>
    <v-list-item to="/fixtures/all">
      <v-list-item-content><v-list-item-title   >All Fixtures</v-list-item-title></v-list-item-content>
    </v-list-item>
    <v-list-item to="/results/submit" v-if="user">
      <v-list-item-content><v-list-item-title  >Submit Results</v-list-item-title></v-list-item-content>
    </v-list-item>
    <v-list-item to="/results/submit/instructions" v-if="!user">
      <v-list-item-content><v-list-item-title  >Submit Results</v-list-item-title></v-list-item-content>
    </v-list-item>
  </ql-side-menu>
  """

  subscription("user")(c => LoginService.userProfile)
}
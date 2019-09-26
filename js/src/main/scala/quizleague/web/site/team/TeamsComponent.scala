package quizleague.web.site.team

import scalajs.js
import quizleague.web.core.RouteComponent
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.site.SideMenu
import quizleague.web.site.login.LoginService

object TeamsComponent extends RouteComponent with GridSizeComponentConfig {
    val template="""
      <v-container v-bind="gridSize" fluid>
        <v-layout>
          <v-flex><ql-text-box><ql-named-text name="teams-header"></ql-named-text></ql-text-box></v-flex>
        </v-layout>
       </v-container>"""
  override val mounted = ({(c:facade) => {
    //super.mounted.call(c)
    LoginService.userProfile.filter(_ != null).subscribe(u => c.$router.push(s"/team/${u.team.id}"))

  }}:js.ThisFunction)

}
object TeamsTitleComponent extends RouteComponent{
   val  template="""
    <v-toolbar      
      color="amber lighten-3"
      dense
      class="subtitle-background"
      >
      <ql-title>Teams</ql-title>
      <v-toolbar-title>
        Teams 
      </v-toolbar-title>
    </v-toolbar>"""

}

object StartTeamPage extends RouteComponent with GridSizeComponentConfig{
    val template="""
      <v-container v-bind="gridSize" fluid>
        <v-layout>
          <v-flex><ql-text-box><ql-named-text name="start-team"></ql-named-text></ql-text-box></v-flex>
        </v-layout>
       </v-container>"""

}

object StartTeamTitleComponent extends RouteComponent{
   val  template="""
    <v-toolbar      
      color="amber lighten-3"
      dense
      class="subtitle-background"
      >
      <ql-title>Starting a Team</ql-title>
      <v-toolbar-title>
        Starting a Team 
      </v-toolbar-title>
    </v-toolbar>"""
       


}


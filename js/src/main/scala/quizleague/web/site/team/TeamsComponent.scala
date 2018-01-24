package quizleague.web.site.team

import quizleague.web.core.RouteComponent
import quizleague.web.core.GridSizeComponentConfig

object TeamsComponent extends RouteComponent with GridSizeComponentConfig{
    val template="""
      <v-container v-bind="gridSize" fluid>
        <ql-title>Teams</ql-title>
        <v-layout>
          <v-flex><ql-named-text name="teams-header"></ql-named-text></v-flex>
        </v-layout>
       </v-container>"""

}
object TeamsTitleComponent extends RouteComponent{
   val  template="""<v-toolbar      
      color="amber darken-3"
      dark
	    
      clipped-left>
      <v-toolbar-title class="white--text" >
        Teams 
      </v-toolbar-title>
    </v-toolbar>"""
       


}

object TeamsMenuComponent extends RouteComponent{
  
  val template = """
<div>  
   <ql-side-menu title="Stuff" icon="people">
    <v-list-tile to="/team/start">
        <v-list-tile-content><v-list-tile-title >Start a team</v-list-tile-title></v-list-tile-content>
    </v-list-tile>
    <v-list-tile to="/team/login">
        <v-list-tile-content><v-list-tile-title >Login</v-list-tile-title></v-list-tile-content>
    </v-list-tile>
  </ql-side-menu>
  <ql-team-menu></ql-team-menu>
</div>
  
"""
  components(TeamMenu)
}
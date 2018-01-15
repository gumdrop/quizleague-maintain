package quizleague.web.site.team

import quizleague.web.core.RouteComponent
import quizleague.web.core.GridSizeComponentConfig

object TeamsComponent extends RouteComponent with GridSizeComponentConfig{
    val template="""<v-container v-bind="gridSize" fluid>
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
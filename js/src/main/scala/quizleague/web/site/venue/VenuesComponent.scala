package quizleague.web.site.venue

import quizleague.web.core.RouteComponent
import quizleague.web.core.GridSizeComponentConfig


object VenuesComponent extends RouteComponent with GridSizeComponentConfig{
   override val template="""
     <v-container v-bind="gridSize" fluid>
       <v-layout>
         <v-flex><ql-named-text name="venues-front-page"></ql-named-text></v-flex>
       </v-layout>
     </v-container>
       """

}
object VenuesTitleComponent extends RouteComponent{
   override val  template="""<v-toolbar      
      color="orange darken-3"
      dark
	    
      clipped-left>
      <v-toolbar-title class="white--text" >
        Venues
      </v-toolbar-title>
    </v-toolbar>"""
       

}
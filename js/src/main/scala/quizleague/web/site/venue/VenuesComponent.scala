package quizleague.web.site.venue

import quizleague.web.core.RouteComponent
import quizleague.web.core.GridSizeComponentConfig


object VenuesComponent extends RouteComponent with GridSizeComponentConfig{
   override val template="""
     <v-container v-bind="gridSize" fluid>
       <v-layout>
         <v-flex><ql-text-box><ql-named-text name="venues-front-page"></ql-named-text></ql-text-box></v-flex>
       </v-layout>
     </v-container>
       """

}
object VenuesTitleComponent extends RouteComponent{
   override val  template="""<v-toolbar      
      color="orange lighten-3"
      dense
      class="subtitle-background"
      >
      <ql-title>Venues</ql-title>
      <v-toolbar-title>
        Venues
      </v-toolbar-title>
    </v-toolbar>"""
       

}
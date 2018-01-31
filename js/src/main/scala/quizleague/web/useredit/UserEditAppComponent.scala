package quizleague.web.useredit

import quizleague.web.core.Component
import scalajs.js


object UserEditAppComponent extends Component {
     val name = "ql-user-edit-app"

     val template="""
  <v-app
    toolbar 
    style="font-size:16px;"
  >
   <v-toolbar      
      color="blue darken-3"
      dark
	    fixed 
      app 
      clipped-left
      scroll-off-screen
      >
      <v-toolbar-side-icon></v-toolbar-side-icon>
      <v-toolbar-title class="white--text" >
        
        <span>Edit Team Details</span>
      </v-toolbar-title>

    </v-toolbar>
    <v-content>
		  <v-container fill-height fluid class="px-0 py-0">
        <v-layout justify-left align-top column>
         <router-view></router-view>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>"""
 
}


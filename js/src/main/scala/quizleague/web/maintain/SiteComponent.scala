package quizleague.web.maintain

import quizleague.web.core.Component
import scalajs.js


object MaintainAppComponent extends Component {
     val name = "ql-maintain-app"

     val template="""
  <v-app
    toolbar 
    style="font-size:16px;"
  >
  <v-navigation-drawer
      clipped
      fixed
      app>
    <router-view name="sidenav"></router-view>
  </v-navigation-drawer>
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
        
        <span>Data Maintenance</span>
      </v-toolbar-title>

    </v-toolbar>
    <v-content>
		  <v-container fill-height fluid class="px-0 py-0">
        <v-layout justify-left align-top column>
         <router-view name="title"  style="z-index:2"></router-view>
         <p></p>
         <router-view ></router-view>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>"""
 
}


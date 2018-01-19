package quizleague.web.site

import quizleague.web.core.Component
import scalajs.js


object SiteComponent extends Component {
     val name = "ql-app"

     val template="""
  <v-app
    toolbar 
    style="font-size:16px;"
  >
  <v-navigation-drawer
      clipped
      fixed
      app
      disable-resize-watcher
	  v-model="drawer">
    <v-list dense v-if="$vuetify.breakpoint.mdAndDown">
      <v-list-group no-action>
            <v-list-tile slot="item" @click="">
              <v-list-tile-action>
                <v-icon>menu</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>Main Menu</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-icon>keyboard_arrow_down</v-icon>
              </v-list-tile-action>
            </v-list-tile>
        <v-list-tile><v-list-tile-action><v-btn to="/home" flat ><v-icon left>home</v-icon><span>Home</span></v-btn></v-list-tile-action></v-list-tile>  
      	<v-list-tile><v-list-tile-action><v-btn to="/team" flat ><v-icon left>people</v-icon><span>Teams</span></v-btn></v-list-tile-action></v-list-tile>
        <v-list-tile><v-list-tile-action><v-btn to="/competition" flat ><v-icon left>mdi-trophy</v-icon><span>Competitions</span></v-btn></v-list-tile-action></v-list-tile>
        <v-list-tile><v-list-tile-action><v-btn to="/results" flat ><v-icon left>check</v-icon><span>Results</span></v-btn></v-list-tile-action></v-list-tile>  
        <v-list-tile><v-list-tile-action><v-btn to="/venue" flat ><v-icon left>location_on</v-icon><span>Venues</span></v-btn></v-list-tile-action></v-list-tile>  
        <v-list-tile><v-list-tile-action><v-btn to="/calendar" flat ><v-icon left>mdi-calendar</v-icon><span>Calendar</span></v-btn></v-list-tile-action></v-list-tile>  
        <v-list-tile><v-list-tile-action><v-btn to="/rules" flat ><v-icon left>mdi-book-open-page-variant</v-icon><span>Rules</span></v-btn></v-list-tile-action></v-list-tile>
        <v-list-tile><v-list-tile-action><v-btn to="/links" flat ><v-icon left>link</v-icon><span>Links</span></v-btn></v-list-tile-action></v-list-tile>  
      </v-list-group>
    </v-list>

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
      <v-toolbar-title class="white--text" >
        <v-toolbar-side-icon @click.stop="drawer = !drawer" v-show="menu"></v-toolbar-side-icon>
        <span v-if="appData">{{appData.leagueName}}</span>
      </v-toolbar-title>
      <div slot="extension" v-if="$vuetify.breakpoint.lgAndUp">
      	<v-btn to="/home" flat ><v-icon left>home</v-icon><span>Home</span></v-btn>
      	<v-btn to="/team" flat ><v-icon left>people</v-icon><span>Teams</span></v-btn>
      	<v-btn to="/competition" flat ><v-icon left>mdi-trophy</v-icon><span>Competitions</span></v-btn>
      	<v-btn to="/results" flat ><v-icon left>check</v-icon><span>Results</span></v-btn>
      	<v-btn to="/venue" flat ><v-icon left>location_on</v-icon><span>Venues</span></v-btn>
      	<v-btn to="/calendar" flat ><v-icon left>mdi-calendar</v-icon><span>Calendar</span></v-btn>
      	<v-btn to="/rules" flat ><v-icon left>mdi-book-open-page-variant</v-icon><span>Rules</span></v-btn>
      	<v-btn to="/links" flat ><v-icon left>link</v-icon><span>Links</span></v-btn>
      </div>
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
  
  data("menu",true)
  subscription("appData")(c => ApplicationContextService.get())
  subscription("drawer")(c => SiteService.sidemenu)

}

trait NoSideMenu{
  this:Component =>
  override val beforeCreate:js.Function = () => SiteService.sidemenu.next(false)
}

trait SideMenu{
  this:Component =>
  override val mounted:js.Function = () => SiteService.sidemenu.next(true)
}
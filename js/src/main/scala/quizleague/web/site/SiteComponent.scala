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
    <ql-side-menu title="Main Menu" icon="menu" v-if="$vuetify.breakpoint.mdAndDown">
        <v-list-tile to="/home" ><v-list-tile-action><v-icon flat left>home</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Home</v-list-tile-title></v-list-tile-content></v-list-tile>  
        <v-list-tile to="/team" ><v-list-tile-action><v-icon flat left>people</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Teams</v-list-tile-title></v-list-tile-content></v-list-tile>  
        <v-list-tile to="/competition" ><v-list-tile-action><v-icon flat left>mdi-trophy</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Competitions</v-list-tile-title></v-list-tile-content></v-list-tile>  
        <v-list-tile to="/results" ><v-list-tile-action><v-icon flat left>check</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Results</v-list-tile-title></v-list-tile-content></v-list-tile>  
        <v-list-tile to="/venue" ><v-list-tile-action><v-icon flat left>location_on</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Venues</v-list-tile-title></v-list-tile-content></v-list-tile>  
        <v-list-tile to="/calendar" ><v-list-tile-action><v-icon flat left>mdi-calendar</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Calendar</v-list-tile-title></v-list-tile-content></v-list-tile>  
        <v-list-tile to="/rules" ><v-list-tile-action><v-icon flat left>mdi-book-open-page-variant</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Rules</v-list-tile-title></v-list-tile-content></v-list-tile>  
        <v-list-tile to="/links" ><v-list-tile-action><v-icon flat left>link</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Links</v-list-tile-title></v-list-tile-content></v-list-tile>  
    </ql-side-menu>

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
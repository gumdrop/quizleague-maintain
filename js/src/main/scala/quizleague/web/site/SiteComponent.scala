package quizleague.web.site

import quizleague.web.core.Component
import scalajs.js
import js.DynamicImplicits._
import com.felstar.scalajs.vue.VuetifyComponent
import com.felstar.scalajs.vue.VueRxComponent

@js.native
trait SiteComponent extends VueRxComponent with VuetifyComponent{
  var sidemenu:Boolean
  var drawer:Boolean
  var showMenu:Boolean
}

object SiteComponent extends Component {
     type facade = SiteComponent
  
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
      :disable-resize-watcher="true"
      :disable-route-watcher="true"
	  v-model="drawer">
	  <v-list :expand="true">
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
    </v-list>
  </v-navigation-drawer>
    <v-toolbar      
      color="blue darken-3"
      dark
	    fixed 
      app 
      clipped-left
      scroll-off-screen
      >
      <v-toolbar-side-icon @click.stop="showMenu = !showMenu" v-show="$vuetify.breakpoint.mdAndDown"></v-toolbar-side-icon>
      <v-toolbar-title class="white--text" >
        
        <span v-if="appData" :class="$vuetify.breakpoint.smAndUp?'page-header':''">{{appData.leagueName}}</span>
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
      <notifications></notifications>
      </v-container>
    </v-content>
  </v-app>"""
     
  components(ResultNotificationsComponent)
  
  def drawerGet(c:facade) = {
       val res = (c.sidemenu && c.$vuetify.breakpoint.lgAndUp) || (c.showMenu && c.$vuetify.breakpoint.mdAndDown)
       println(s"drawer get : $res")
       res    
     }
  
  data("showMenu",false)
  subscription("appData")(c => ApplicationContextService.get())
  subscription("sidemenu")(c => SiteService.sidemenu)
  computedGetSet("drawer")({drawerGet _}:js.ThisFunction)({(c:facade, showMenu:Boolean) => {println(s"incoming : $showMenu");}}:js.ThisFunction)
  //watch("drawer")((c:facade, drawer:js.Any) => println(s"drawer : ${c.drawer}"))
}

trait NoSideMenu{
  this:Component =>
  override def mounted:js.Function = () => { SiteService.sidemenu.next(false)}
}

trait SideMenu{
  this:Component =>
  override def mounted:js.Function = () => {SiteService.sidemenu.next(true)}
}


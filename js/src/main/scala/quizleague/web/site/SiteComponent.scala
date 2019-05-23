package quizleague.web.site

import quizleague.web.core.Component

import scalajs.js
import js.DynamicImplicits._
import com.felstar.scalajs.vue.VuetifyComponent
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.site.login.LoginService
import quizleague.web.util.Logging._

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
      
      app
      :disable-resize-watcher="true"
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
        <v-list-tile to="/contact" ><v-list-tile-action><v-icon flat left>contact_mail</v-icon></v-list-tile-action><v-list-tile-content><v-list-tile-title>Contact Us</v-list-tile-title></v-list-tile-content></v-list-tile>  

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
      <v-toolbar-side-icon @click.stop="drawer = !drawer" v-show="$vuetify.breakpoint.mdAndDown"></v-toolbar-side-icon>
      <v-toolbar-title class="white--text" >
        
        <span v-if="appData" :class="$vuetify.breakpoint.smAndUp?'page-header':'page-header-small'"><ql-title :title="appData.leagueName"></ql-title></span>

      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <ql-logged-on-menu :user="user"  v-if="user"></ql-logged-on-menu>
        <v-btn to="/login" flat fab v-if="!user" title="Login"><v-icon left>mdi-login</v-icon></v-btn>
      </v-toolbar-items>
      <div slot="extension" v-if="$vuetify.breakpoint.lgAndUp">
      	<v-btn to="/home" flat ><v-icon left>home</v-icon><span>Home</span></v-btn>
      	<v-btn to="/team" flat ><v-icon left>people</v-icon><span>Teams</span></v-btn>
      	<v-btn to="/competition" flat ><v-icon left>mdi-trophy</v-icon><span>Competitions</span></v-btn>
      	<v-btn to="/results" flat ><v-icon left>check</v-icon><span>Results</span></v-btn>
      	<v-btn to="/venue" flat ><v-icon left>location_on</v-icon><span>Venues</span></v-btn>
      	<v-btn to="/calendar" flat ><v-icon left>mdi-calendar</v-icon><span>Calendar</span></v-btn>
      	<v-btn to="/rules" flat ><v-icon left>mdi-book-open-page-variant</v-icon><span>Rules</span></v-btn>
      	<v-btn to="/links" flat ><v-icon left>link</v-icon><span>Links</span></v-btn>
      	<v-btn to="/contact" flat ><v-icon left>contact_mail</v-icon><span>Contact Us</span></v-btn>

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
     
  components(ResultNotificationsComponent,TitleComponent, LoggedOnMenu)
  
  def drawerGet(c:facade) = (c.sidemenu && c.$vuetify.breakpoint.lgAndUp) || (c.showMenu && c.$vuetify.breakpoint.mdAndDown)
  def drawerSet(c:facade, showMenu:Boolean){c.showMenu = if(c.$vuetify.breakpoint.mdAndDown) showMenu else c.showMenu}

  
  data("showMenu",false)
  subscription("appData")(c => ApplicationContextService.get())
  subscription("sidemenu")(c => SiteService.sidemenu)
  subscription("user")(c => LoginService.userProfile)
  computedGetSet("drawer")({drawerGet _}:js.ThisFunction)({drawerSet _}:js.ThisFunction)
}

object LoggedOnMenu extends Component{
  val name = "ql-logged-on-menu"
  val template = """
  <v-menu offset-y>
    <template v-slot:activator="{ on }">
      <v-btn flat fab v-on="on" small><v-avatar size="36" :title="user.siteUser.handle"><img :src="user.siteUser.avatar"></img></v-avatar></v-btn>
    </template>
    <v-list>
        <v-list-tile to="/login/profile" key="1">
          <v-list-tile-action><v-icon flat left>person</v-icon></v-list-tile-action>
          <v-list-tile-content><v-list-tile-title>Edit Profile</v-list-tile-title></v-list-tile-content>
        </v-list-tile>
        <v-list-tile key="2" @click="logout()">
          <v-list-tile-action><v-icon flat left>mdi-logout</v-icon></v-list-tile-action>
          <v-list-tile-content><v-list-tile-title>Logout</v-list-tile-title></v-list-tile-content>
        </v-list-tile>
      </v-list>
  </v-menu>
  """

  props("user")

  method("logout"){LoginService.logout _}
}

trait NoSideMenu{
  this:Component =>
  override def mounted:js.Function = () => { SiteService.sidemenu.next(false)}
}

trait SideMenu{
  this:Component =>
  override def mounted:js.Function = () => {SiteService.sidemenu.next(true)}
}

object TitleComponent extends Component{
  val name = "ql-title"
  val template =
    """
      <span>
        <span class="page-header-first">{{first(title)}}</span>
        <span class="page-header-rest"> {{rest(title)}}</span>
      </span>"""

  prop("title")

  method("first")((title:String) => title.split(" ")(0))
  method("rest")((title:String) => title.split(" ").drop(1).mkString(" "))
}


package quizleague.web.site

import quizleague.web.core.Component

import scalajs.js
import js.DynamicImplicits._
import com.felstar.scalajs.vue.VuetifyComponent
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.site.login.LoginService
import quizleague.web.core._
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
    style="font-size:16px;"
  >
  <v-navigation-drawer
      clipped
      width="280"
      app
      :disable-resize-watcher="true"
	  v-model="drawer">
	  <v-list :expand="true" nav tile shaped dense tile>
    <ql-side-menu title="Main Menu" icon="mdi-menu" v-if="$vuetify.breakpoint.mdAndDown">
      <v-list-item v-for="item in items" :to="item.to" ><v-list-item-action><v-icon text left v-text="item.icon"></v-icon></v-list-item-action><v-list-item-content><v-list-item-title v-text="item.name"></v-list-item-title></v-list-item-content></v-list-item>
    </ql-side-menu>
    <router-view name="sidenav"></router-view>
    </v-list>
  </v-navigation-drawer>
    <v-app-bar
      color="blue darken-3"
      dark
	    fixed
      app
      clipped-left
      hide-on-scroll
      src="/img/chiltern-hills.jpg"
      >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" v-show="$vuetify.breakpoint.mdAndDown"></v-app-bar-nav-icon>
      <v-toolbar-title class="white--text" >

        <span v-if="appData" :class="$vuetify.breakpoint.smAndUp?'page-header':'page-header-small'"><ql-title :title="appData.leagueName"></ql-title></span>

      </v-toolbar-title>
      <v-spacer></v-spacer>
      <div v-if="$vuetify.breakpoint.mdAndUp">
      <v-tooltip left>
        <template v-slot:activator="{ on }">
          <v-btn text icon fab v-on="on" target="_blank" href="https://www.facebook.com/ChilternQuizLeague/" tooltip="Facebook"><v-icon>mdi-facebook-box</v-icon></v-btn>
        </template>
        <span>Facebook</span>
      </v-tooltip>

        <ql-logged-on-menu :user="user"  v-if="user"></ql-logged-on-menu>
        <v-btn to="/login" text fab v-if="!user" title="Login"><v-icon left>mdi-login</v-icon></v-btn>
      </div>
      <div slot="extension" v-if="$vuetify.breakpoint.lgAndUp">
        <v-toolbar
          color="transparent"
          dark
          dense
          flat>
          <v-toolbar-items>
            <v-btn text v-for="item in items" :to="item.to" ><v-icon left>{{item.icon}}</v-icon><span>{{item.name}}</span></v-btn>
          </v-toolbar-items>
        </v-toolbar>
      </div>
      <template v-slot:img="{ props }" >
        <v-img v-if="$vuetify.breakpoint.smAndUp"
          v-bind="props"
          gradient="to top right, rgba(19,84,122,.5), rgba(128,208,199,.8)"
        ></v-img>
      </template>
      </template>
    </v-app-bar>

    <v-content >
      <div class="frame-background" v-if="$vuetify.breakpoint.smAndUp">&nbsp;</div>
		  <v-container fluid class="px-0 py-0" >
        <v-layout justify-left align-top column>
         <router-view name="title"  style="z-index:2"></router-view>
         <p></p>
         <v-flex fill-height style="z-index:1;">
          <router-view  fill-height ></router-view>
         </v-flex>
         </v-layout>
      <notifications></notifications>
      </v-container>
    </v-content>

      <v-bottom-navigation fixed app hide-on-scroll :value="true" v-if="$vuetify.breakpoint.smAndDown" >
        <v-btn text target="_blank" href="https://www.facebook.com/ChilternQuizLeague/" title="Facebook"><span>Facebook</span><v-icon>mdi-facebook-box</v-icon></v-btn>
        <ql-logged-on-menu :user="user"  v-if="user"><span style="position:relative;top:2px;">{{user.siteUser.handle}}</span></ql-logged-on-menu>
        <v-btn to="/login" text v-if="!user" title="Login"><span>Login</span><v-icon>mdi-login</v-icon></v-btn>
      </v-bottom-navigation>
  </v-app>"""

  components(ResultNotificationsComponent,TitleComponent, LoggedOnMenu)

  def drawerGet(c:facade) = (c.sidemenu && c.$vuetify.breakpoint.lgAndUp) || (c.showMenu && c.$vuetify.breakpoint.mdAndDown)
  def drawerSet(c:facade, showMenu:Boolean){c.showMenu = if(c.$vuetify.breakpoint.mdAndDown) showMenu else c.showMenu}


  data("showMenu",false)
  data("items",
    @@(menuItem("Home", "/home", "mdi-home"),
      menuItem("Teams", "/team", "mdi-account-multiple"),
      menuItem("Competitions", "/competition", "mdi-trophy"),
      menuItem("Results", "/results", "mdi-check"),
      menuItem("Venues", "/venue", "mdi-map-marker"),
      menuItem("Calendar", "/calendar", "mdi-calendar"),
      menuItem("Rules", "/rules", "mdi-book-open-page-variant"),
      menuItem("Links", "/links", "mdi-link"),
      menuItem("Contact Us", "/contact", "mdi-contact-mail")

    ))
  subscription("appData")(c => ApplicationContextService.get())
  subscription("sidemenu")(c => SiteService.sidemenu)
  subscription("user")(c => LoginService.userProfile)
  computedGetSet("drawer")({drawerGet _}:js.ThisFunction)({drawerSet _}:js.ThisFunction)

  def menuItem(name:String, to:String, icon:String) = $(name=name, to=to, icon=icon)
}

object LoggedOnMenu extends Component{
  val name = "ql-logged-on-menu"
  val template = """
  <v-menu offset-y>
    <template v-slot:activator="{ on }">
      <v-btn text fab icon v-on="on" small :title="user.siteUser.handle"><slot></slot><v-avatar size="24" ><img :src="user.siteUser.avatar"></img></v-avatar></v-btn>
    </template>
    <v-list>
        <v-list-item to="/login/profile" key="1">
          <v-list-item-action><v-icon text left>mdi-account</v-icon></v-list-item-action>
          <v-list-item-content><v-list-item-title>Edit Profile</v-list-item-title></v-list-item-content>
        </v-list-item>
        <v-list-item key="2" @click="logout()">
          <v-list-item-action><v-icon text left>mdi-logout</v-icon></v-list-item-action>
          <v-list-item-content><v-list-item-title>Logout</v-list-item-title></v-list-item-content>
        </v-list-item>
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


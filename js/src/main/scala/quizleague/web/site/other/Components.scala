package quizleague.web.site.other

import quizleague.web.core._
import quizleague.web.site._

object LinksComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{
  val template = """
  <v-container v-bind="gridSize" fluid>
    <v-layout>
    <v-flex><ql-named-text name="links-content"></ql-named-text></v-flex>
    </v-layout>
  </v-container>"""  
}

object LinksTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="green darken-3"
      dark
      clipped-left>
      <ql-title>Links</ql-title>
      <v-toolbar-title class="white--text" >
        Links
      </v-toolbar-title>
    </v-toolbar>"""
}

object RulesComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{
  val template = """
  <v-container v-bind="gridSize" fluid>
  <ql-title>Rules</ql-title>
    <v-layout>
    <v-flex><ql-named-text name="rules-content"></ql-named-text></v-flex>
    </v-layout>
  </v-container>"""  
}

object SideMenuHeader extends Component{
  val name = "ql-side-menu-header"
  
  val template = """
            <v-list-tile slot="item" @click="">
              <v-list-tile-action>
                <v-icon>{{icon}}</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>{{title}}</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-icon>keyboard_arrow_down</v-icon>
              </v-list-tile-action>
            </v-list-tile>"""
  
  props("title","icon")
}
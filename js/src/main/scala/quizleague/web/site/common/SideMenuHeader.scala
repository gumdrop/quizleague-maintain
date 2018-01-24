package quizleague.web.site.common

import quizleague.web.core._

object SideMenu extends Component{
  val name = "ql-side-menu"
  
  val template = """
     <v-list dense>
     <v-list-group no-action :value="true">
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
            </v-list-tile>
            <slot></slot>
      </v-list-group>
    </v-list>


"""
  
  props("title","icon")
}
package quizleague.web.site.common

import quizleague.web.core._

object SideMenu extends Component{
  val name = "ql-side-menu"
  
  val template = """
     <v-list-group no-action :prepend-icon="icon" :key="title" v-model="active" >
            <v-list-tile slot="activator" >
              <v-list-tile-content>
                <v-list-tile-title>{{title}}</v-list-tile-title>
              </v-list-tile-content>
            </v-list-tile>
            <slot></slot>
      </v-list-group>

"""
  data("active",true)
  props("title","icon")
}
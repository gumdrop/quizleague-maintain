package quizleague.web.site.common

import quizleague.web.core._

object SideMenu extends Component{
  val name = "ql-side-menu"
  
  val template = """
     <v-list-group no-action :prepend-icon="icon" :key="title" v-model="active">
        <template v-slot:activator>
          <v-list-item-title v-text="title"></v-list-item-title>
        </template>
        <slot></slot>
      </v-list-group>

"""
  data("active",true)
  props("title","icon")
}
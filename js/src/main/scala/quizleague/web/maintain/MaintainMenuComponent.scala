package quizleague.web.maintain

import quizleague.web.core._

object MaintainMenuComponent extends Component{
  
  val name = "ql-maintain-menu"
  
  val template = """
     <v-list >
        <v-list-item to="/maintain/applicationcontext"><v-list-item-content><v-list-item-title>Application Context</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/globaltext"><v-list-item-content><v-list-item-title>Global Text</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/team"><v-list-item-content><v-list-item-title>Teams</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/season"><v-list-item-content><v-list-item-title>Seasons</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/user"><v-list-item-content><v-list-item-title>Users</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/venue"><v-list-item-content><v-list-item-title>Venues</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/database"><v-list-item-content><v-list-item-title>Database</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/stats"><v-list-item-content><v-list-item-title>Statistics</v-list-item-title></v-list-item-content></v-list-item>
        <v-list-item to="/maintain/competitionstatistics"><v-list-item-content><v-list-item-title>Competition Statistics</v-list-item-title></v-list-item-content></v-list-item>

     </v-list>
"""
  
}

